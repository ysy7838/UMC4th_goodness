package umc.precending.controller.Person;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import umc.precending.domain.member.Member;
import umc.precending.domain.member.Person;
import umc.precending.dto.Recommend.LeftRandomRecommendCountDto;
import umc.precending.dto.Recommend.SingleRecommendShowDto;
import umc.precending.exception.member.MemberNotFoundException;
import umc.precending.repository.member.MemberRepository;
import umc.precending.repository.member.PersonRepository;
import umc.precending.response.Response;
import umc.precending.service.Member.MemberGroupService;
import umc.precending.service.recommend.RecommendService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend_good")
public class RecommendController {

    private final RecommendService recommendService;
    private final PersonRepository personRepository;


    @PatchMapping("/show/MyRecommend/{num}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "나의 추천 선행목록을 하나 보여줍니다,그리고 열어본 점수를 더합니다", notes = "나의 추천 선행 목록을 보여주는 로직")
    public Response showOne(@PathVariable int num){
        Person findPerson=getPerson();
        SingleRecommendShowDto singleRecommendShowDto=recommendService.recommendSingleShowDto(num,findPerson);
        return Response.success(singleRecommendShowDto);
    }

    @PatchMapping("/save/Recommend/{num}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="나의 추천 선행목록을 저장합니다.",notes = "나의 추천 선행 목록을 저장하는 로직")
    public void saveRecommend(@PathVariable int num){
        Person findPerson=getPerson();
        recommendService.RecommendSave(findPerson,num);
    }

    @DeleteMapping("/cancel/Recommend/{num}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value="나의 추천 선행목록을 저장한 것을 바로 취소합니다.",notes = "나의 추천 선행 목록을 취소하는 로직")
    public void cancelSaveRecommend(@PathVariable int num){
        Person findPerson=getPerson();
        recommendService.cancelSaveRecommend(findPerson,num);
    }


    @PatchMapping("/change/MyRecommend")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "나의 추천 선행 목록을 바꿉니다", notes = "나의 추천 선행 목록을 바꾸는 로직")
    public void changeAll(){
        Person findPerson=getPerson();
        recommendService.changeMyRecommendAll(findPerson);
    }

    @PatchMapping("/make/changeable/Recommend")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "나의 추천 선행 목록을 바꿀수 있는 상태로 바꿉니다", notes = "나의 추천 선행 목록을 바꿀수 있는 상태로 바꾸는 로직")
    public void makeChangeable(){
        Person findPerson=getPerson();
        recommendService.makeChangeableRecommendation(findPerson);
    }


    @GetMapping("/show/MyRecommend/count")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="사용자가 자신의 추천 선행을 다시 랜덤으로 돌릴 수 있는 기회를 보여줍니다",notes = "자신의 추천 선행을 랜덤으로 돌릴 수 있는 횟수를 보여주는 로직")
    public Response showRecommendRandomCount(){
        Person findPerson=getPerson();
        LeftRandomRecommendCountDto leftRandomRecommendCountDto=new LeftRandomRecommendCountDto(recommendService.showLeftRandomCount(findPerson));
        return Response.success(leftRandomRecommendCountDto);
    }

    private Person getPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return personRepository.findPersonByUsername(username).orElseThrow(MemberNotFoundException::new);
    }

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="매일 8시가 되면 모든 사람의 추천 선행이 바뀌는데 그걸 해볼수 있는 테스트입니다",notes = "테스트로직")
    public void test(){
        recommendService.changeAllPersonRecommendTest();
    }

}
