package umc.precending.service.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.precending.domain.Recommend.MemberTodayRecommend;
import umc.precending.domain.Recommend.Recommend;
import umc.precending.domain.member.*;
import umc.precending.dto.person.NameScoreClubDto;
import umc.precending.dto.person.NameScoreCorporateDto;
import umc.precending.exception.RecommendGoodness.CannotChangeableRecommendException;
import umc.precending.repository.memberTodayRecommendRepository.MemberTodayRecommendRepository;
import umc.precending.repository.recommendRepository.RecommendRepository;
import umc.precending.repository.member.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGroupService {
    private final MemberRepository memberRepository;
    private final RecommendRepository recommendRepository;
    private final MemberTodayRecommendRepository memberTodayRecommendRepository;
    private final CorporateRepository corporateRepository;
    private final ClubRepository clubRepository;
    private final Person_ClubRepository personClubRepository;
    private final Person_CorporateRepository personCorporateRepository;


   //가장 점수를 많이 받은 상위 5개 corporate의 이름과 점수를 보기
    public List<NameScoreCorporateDto> showHighestScoreCorporate(){
        List<Corporate> corporates=corporateRepository.findTop5ByScoreGreaterThanOrderByScoreDesc(0);
        List<NameScoreCorporateDto> result=corporates.stream().map(c->new NameScoreCorporateDto(c)).collect(Collectors.toList());
        return result;
    }
    //가장 점수를 많이 받은 상위 5개 club의 이름과 점수를 보기
    public List<NameScoreClubDto> showHighestScoreClub(){
        List<Club> clubs=clubRepository.findTop5ByScoreGreaterThanOrderByScoreDesc(0);
        List<NameScoreClubDto> result=clubs.stream().map(c->new NameScoreClubDto(c)).collect(Collectors.toList());
        return result;
    }

    //매월 1일이 되면 club과 corporate의 점수가 0이되고 사용자의 응원또한 초기화된다.
    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void resetScore(){
        clubRepository.findAll().forEach(c->c.resetScore());
        corporateRepository.findAll().forEach(c->c.resetScore());
        personClubRepository.deleteAll();
        personCorporateRepository.deleteAll();
    }
}