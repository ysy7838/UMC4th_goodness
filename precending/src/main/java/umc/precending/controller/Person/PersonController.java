package umc.precending.controller.Person;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import umc.precending.domain.member.Member;
import umc.precending.dto.person.ClubIdDto;
import umc.precending.dto.person.CorporateIdDto;
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
    @ApiImplicitParam(name = "corporateIdDto", value = "기업의 이메일을 입력해주세요")
    public void addCorporate(@RequestBody CorporateIdDto corporateIdDto) {
        Member findMember=getMember();
        personService.addScoreCorporate(corporateIdDto.getCorporateId(),findMember);
    }
    @DeleteMapping ("/cancel/cheer/corporate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "개인이 기업에 점수 준 것을 취소하기", notes = "개인이 기업에 점수를 준 것을 취소하는 로직")
    @ApiImplicitParam(name = "corporateIdDto", value = "기업의 이메일을 입력해주세요")
    public void cancelCorporate(@RequestBody CorporateIdDto corporateIdDto) {
        Member findMember=getMember();
        personService.cancelScoreCorporate(corporateIdDto.getCorporateId(),findMember);
    }
    @PatchMapping ("/add/cheer/club")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "개인이 동아리에 점수 주기", notes = "개인이 동아리에 점수를 주는 로직")
    @ApiImplicitParam(name = "clubIdDto", value = "동아리의 이메일을 입력해주세요")
    public void addClub(@RequestBody ClubIdDto clubIdDto) {
        Member findMember=getMember();
        personService.addScoreClub(clubIdDto.getClubId(),findMember);
    }

    @DeleteMapping ("/cancel/cheer/club")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "개인이 동아리에 점수 준 것을 취소하기", notes = "개인이 동아리에 점수를 준 것을 취소하는 로직")
    @ApiImplicitParam(name = "clubIdDto", value = "동아리의 이메일을 입력해주세요")
    public void cancelClub(@RequestBody ClubIdDto clubIdDto) {
        Member findMember=getMember();
        personService.cancelScoreClub(clubIdDto.getClubId(),findMember);
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