package umc.precending.controller.member;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.precending.domain.member.Member;
import umc.precending.exception.member.MemberNotFoundException;
import umc.precending.repository.member.MemberRepository;
import umc.precending.service.member.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/member/image")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "이미지 저장", notes = "회원의 프로필 이미지를 저장하는 로직")
    @ApiImplicitParam(name = "file", value = "회원의 프로필 이미지 설정을 위한 이미지 파일")
    public void saveImage(@ModelAttribute MultipartFile file) {
        Member member = getMember();
        memberService.saveImage(file, member);
    }

    // 현재 로그인하고 있는 사용자의 정보를 통해 정보 반환
    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return memberRepository.findMemberByUsername(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
    }
}
