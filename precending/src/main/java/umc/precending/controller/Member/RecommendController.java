package umc.precending.controller.Member;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umc.precending.dto.member.LeftRandomRecommendCountDto;
import umc.precending.dto.member.RecommendShowDto;
import umc.precending.response.Response;
import umc.precending.service.Member.MemberGroupService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend_good")
public class RecommendController {

    private final MemberGroupService memberService;


    @GetMapping("/show/MyRecommend")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "나의 추천 선행목록을 보여줍니다", notes = "나의 추천 선행 목록을 보여주는 로직")
    public Response showAll(){
        RecommendShowDto recommendShowDto=memberService.recommendShowDto();
        return Response.success(recommendShowDto);
    }


    @PatchMapping("/change/MyRecommend")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "나의 추천 선행 목록을 바꿉니다", notes = "나의 추천 선행 목록을 바꾸는 로직")
    public void changeAll(){
        memberService.changeMyRecommendAll();
    }

    @PatchMapping("/make/changeable/Recommend")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "나의 추천 선행 목록을 바꿀수 있는 상태로 바꿉니다", notes = "나의 추천 선행 목록을 바꿀수 있는 상태로 바꾸는 로직")
    public void makeChangeable(){
        memberService.makeChangeableRecommendation();
    }

    @PatchMapping("/add/cofrc/score")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "사용자가 나의 추천 선행 목록을 눌러서 확인할때 이걸 호출하십시오. ", notes = "지금까지 추천 선행을 열어본 점수를 member에 저장하는 로직")
    public void addCofrc(){
        memberService.addCofrc();
    }


    @GetMapping("/show/MyRecommend/count")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="사용자가 자신의 추천 선행을 다시 랜덤으로 돌릴 수 있는 기회를 보여줍니다",notes = "자신의 추천 선행을 랜덤으로 돌릴 수 있는 횟수를 보여주는 로직")
    public Response showRecommendRandomCount(){
        LeftRandomRecommendCountDto leftRandomRecommendCountDto=new LeftRandomRecommendCountDto(memberService.showLeftRandomCount());
        return Response.success(leftRandomRecommendCountDto);
    }

}
