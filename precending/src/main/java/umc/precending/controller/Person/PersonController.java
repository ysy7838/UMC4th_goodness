package umc.precending.controller.Person;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import umc.precending.domain.member.Member;
import umc.precending.dto.person.AddPointClubDto;
import umc.precending.dto.person.AddPointCorporateDto;
import umc.precending.exception.member.MemberNotFoundException;
import umc.precending.repository.member.MemberRepository;
import umc.precending.service.PersonService.PersonService;
import umc.precending.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;
    private final MemberRepository memberRepository;

    @PatchMapping("/add/cheer/corporate")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "개인이 기업에 점수 주기", notes = "개인이 기업에 점수를 주는 로직")
    @ApiImplicitParam(name = "addPointCorporateDto", value = "기업의 이메일을 입력해주세요")
    public void addCorporate(@RequestBody AddPointCorporateDto addPointCorporateDto) {
        Member findMember=getMember();
        personService.addScoreCorporate(addPointCorporateDto.getCorporateId(),findMember);
    }
    @PatchMapping ("/add/cheer/club")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "개인이 동아리에 점수 주기", notes = "개인이 동아리에 점수를 주는 로직")
    @ApiImplicitParam(name = "addPointClubDto", value = "동아리의 이메일을 입력해주세요")
    public void addClub(@RequestBody AddPointClubDto addPointClubDto) {
        Member findMember=getMember();
        personService.addScoreClub(addPointClubDto.getClubId(),findMember);
    }


    @GetMapping("/show/myCorporate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "내가 응원한 기업을 보기", notes = "내가 응원한 기업을 볼 수 있는 로직")
    public Response showMyCorporate(){
        Member findMember=getMember();
        return Response.success(personService.showMyCorporate(findMember));
    }

    @GetMapping("/show/myClub")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "내가 응원한 동아리를 보기", notes = "내가 응원한 동아리를 볼 수 있는 로직")
    public Response showMyClub(){
        Member findMember=getMember();
        return Response.success(personService.showMyClub(findMember));
    }


    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return memberRepository.findMemberByUsername(username).orElseThrow(MemberNotFoundException::new);
    }

}