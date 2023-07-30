package umc.precending.controller.admin;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import umc.precending.domain.Recommend.Recommend;
import umc.precending.dto.admin.RecommendCreateDto;
import umc.precending.repository.recommendRepository.RecommendRepository;
import umc.precending.service.Member.MemberGroupService;
import umc.precending.service.recommend.RecommendService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend_good")
public class TodayRecommendAdminController {

    private final RecommendService recommendService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "타입을 가진 recommend 생성", notes = "recommend 생성하는 로직")
    public void makeRecommendPost(@ModelAttribute @Valid RecommendCreateDto createDto) {
        recommendService.makeRecommend(createDto);
    }




}