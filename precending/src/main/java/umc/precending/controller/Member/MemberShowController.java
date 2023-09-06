package umc.precending.controller.member;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import umc.precending.response.Response;
import umc.precending.service.member.MemberGroupService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberShowController {
    private final MemberGroupService memberGroupService;

    @GetMapping("/show/top5/Corporate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="가장 많은 점수를 받은 상위 5개 기업을 보기",notes = "가장 많은 점수를 받은 상위 5개 기업을 볼 수 있는 로직")
    public Response showTop5Corporate(){
        return Response.success(memberGroupService.showHighestScoreCorporate());
    }

    @GetMapping("/show/top5/Club")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="가장 많은 점수를 받은 상위 5개 동아리 보기",notes = "가장 많은 점수를 받은 상위 5개 동아리를 볼 수 있는 로직")
    public Response showTop5Club(){
        return Response.success(memberGroupService.showHighestScoreClub());
    }

    @GetMapping("/show/random/corporate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "랜덤으로 하나의 corporate를 보여줍니다",notes = "랜덤으로 corporate를 보여주는 로직")
    public Response showRandomCorporate(){
        return Response.success(memberGroupService.corporateShowRandom());
    }

    @GetMapping("/show/random/club")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "랜덤으로 하나의 club을 보여줍니다",notes = "랜덤으로 club을 보여주는 로직")
    public Response showRandomClub(){
        return Response.success(memberGroupService.clubShowRandom());
    }

}
