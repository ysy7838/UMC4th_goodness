package umc.precending.controller.admin;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import umc.precending.domain.member.Recommend;
import umc.precending.dto.admin.NewGoodnessDto;
import umc.precending.repository.member.RecommendRepository;
import umc.precending.service.Member.MemberGroupService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend_good")
public class TodayRecommendAdminController {

    private final MemberGroupService memberService;
    private final RecommendRepository recommendRepository;


    @Transactional
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "추천 선행 저장", notes = "추천 선행 저장을 위한 로직")
    @ApiImplicitParam(name = "newGoodnessDto", value = "관리자는 추천 선행 저장을 위해 선행을 입력해주세요")
    public void saveGood(@RequestBody NewGoodnessDto newGoodnessDto){
        Recommend recommend=new Recommend(newGoodnessDto.getGood());
        recommendRepository.save(recommend);
    }




}