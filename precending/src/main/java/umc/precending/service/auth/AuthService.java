package umc.precending.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.precending.config.jwt.TokenProvider;
import umc.precending.domain.Recommend.Recommend;
import umc.precending.domain.member.*;
import umc.precending.dto.auth.*;
import umc.precending.dto.token.TokenDto;
import umc.precending.dto.token.TokenRequestDto;
import umc.precending.dto.token.TokenResponseDto;
import umc.precending.exception.member.MemberDuplicateException;
import umc.precending.exception.member.MemberLoginFailureException;
import umc.precending.exception.member.MemberNotFoundException;
import umc.precending.repository.recommendRepository.RecommendRepository;
import umc.precending.repository.member.*;
import umc.precending.service.redis.RedisService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PersonRepository personRepository;
    private final CorporateRepository corporateRepository;
    private final ClubRepository clubRepository;
    private final RedisService redisService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RecommendRepository recommendRepository;

    // 회원가입 - 개인
    @Transactional
    public void signUp(MemberPersonSignUpDto signUpDto) {
        Person personMember = getPersonMember(signUpDto);
        List<Recommend> recommends = recommendRepository.selectRandom();
        personMember.setMyTodayRecommendList(recommends);
        memberRepository.save(personMember);
    }

    // 회원가입 - 동아리
    @Transactional
    public void signUp(MemberClubSignUpDto signUpDto) {
        Club clubMember = getClubMember(signUpDto);
        List<Recommend> recommends=recommendRepository.selectRandom();
        clubMember.setMyTodayRecommendList(recommends);
        memberRepository.save(clubMember);
    }

    //회원가입 - 기업
    @Transactional
    public void signUp(MemberCorporateSignUpDto signUpDto) {
        Corporate corporateMember = getCorporateMember(signUpDto);
        List<Recommend> recommends=recommendRepository.selectRandom();
        corporateMember.setMyTodayRecommendList(recommends);
        memberRepository.save(corporateMember);
    }

    // 로그인
    @Transactional
    public TokenResponseDto signIn(MemberSignInDto signInDto) {
        checkLoginValidation(signInDto);

        UsernamePasswordAuthenticationToken authenticationToken = signInDto.getAuthenticationToken();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenValue = tokenProvider.createToken(authentication);
        redisService.saveValue(authentication.getName(), tokenValue.getRefreshToken());

        return new TokenResponseDto(tokenValue.getAccessToken(), tokenValue.getRefreshToken());
    }

    // 만료기간이 다 된 토큰을 재발급받는 로직
    @Transactional
    public TokenResponseDto reIssue(TokenRequestDto requestDto) {
        String accessToken = requestDto.getAccessToken();
        String refreshToken = requestDto.getRefreshToken();

        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        redisService.checkValidation(authentication.getName(), refreshToken);

        TokenDto token = tokenProvider.createToken(authentication);

        redisService.saveValue(authentication.getName(), token.getRefreshToken());

        return new TokenResponseDto(token.getAccessToken(), token.getRefreshToken());
    }

    // 사용자가 로그인을 수행하면서 입력한 정보와 일치하는 계정이 있는지 확인하는 로직
    private void checkLoginValidation(MemberSignInDto signInDto) {
        Member findMember = memberRepository.findMemberByUsername(signInDto.getUsername())
                .orElseThrow(MemberNotFoundException::new);

        if(!checkPwValidation(findMember, signInDto.getPassword())) throw new MemberLoginFailureException();
    }

    // 비밀번호가 일치하는지 확인하는 로직
    private boolean checkPwValidation(Member member, String raw) {
        return passwordEncoder.matches(raw, member.getPassword());
    }

    // 입력한 정보를 바탕으로 유효성을 검사한 뒤, Person 객체를 반환하는 로직
    private Person getPersonMember(MemberPersonSignUpDto signUpDto) {
        if(personRepository.existsPersonByEmail(signUpDto.getEmail())) throw new MemberDuplicateException();

        return new Person(signUpDto.getName(), signUpDto.getBirth(),
                passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getEmail());
    }

    // 입력한 정보를 바탕으로 유효성을 검사한 뒤, Club 객체를 반환하는 로직
    private Club getClubMember(MemberClubSignUpDto signUpDto) {
        if(clubRepository.existsClubByEmail(signUpDto.getEmail())) throw new MemberDuplicateException();

        return new Club(signUpDto.getName(), signUpDto.getBirth(), passwordEncoder.encode(signUpDto.getPassword()),
                signUpDto.getEmail(), signUpDto.getType(), signUpDto.getSchool(), signUpDto.getAddress());
    }

    // 입력한 정보를 바탕으로 유효성을 검사한 뒤, Corporate 객체를 반환하는 로직
    private Corporate getCorporateMember(MemberCorporateSignUpDto signUpDto) {
        if(corporateRepository.existsCorporateByRegistrationNumberOrEmail(signUpDto.getRegistrationNumber(), signUpDto.getEmail()))
            throw new MemberDuplicateException();

        return new Corporate(signUpDto.getName(), signUpDto.getBirth(), passwordEncoder.encode(signUpDto.getPassword()),
                signUpDto.getEmail(), signUpDto.getRegistrationNumber());
    }
}
