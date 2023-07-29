package umc.precending.controller.Member;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import umc.precending.response.Response;
import umc.precending.service.Member.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/show/top5/Corporate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="가장 많은 점수를 받은 기업을 보기",notes = "가장 많은 점수를 받은 상위 5개 기업을 볼 수 있는 로직")
    public Response showTop5Corporate(){
        return Response.success(memberService.showHighestScoreCorporate());
    }



    @GetMapping("/show/top5/Club")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="가장 많은 점수를 받은 동아리 보기",notes = "가장 많은 점수를 받은 상위 5개 동아리를 볼 수 있는 로직")
    public Response showTop5Club(){
        return Response.success(memberService.showHighestScoreClub());
    }

}
