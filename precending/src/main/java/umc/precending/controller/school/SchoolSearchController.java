package umc.precending.controller.school;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umc.precending.domain.school.Search;
import umc.precending.dto.school.SearchDto;
import umc.precending.response.Response;
import umc.precending.service.school.SchoolSearchService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api")
public class SchoolSearchController {
    private final SchoolSearchService searchService;

    // JSON 형식으로 key에 학교 종류(초등학교, 중학교, 고등학교, 대학교, 기타), keyword에 검색할 키워드 (홍익/명지/숙명 등) 입력하면 됩니다.
    @GetMapping("/auth/sign-up/club/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="동아리 검색 결과", notes = "동아리 소속 학교 검색 결과를 노출하기 위한 로직")
    public Response searchResult(@RequestBody @Valid SearchDto searchDto) {
        Search search = searchDto.toSearch();
        return Response.success(searchService.getSchool(search));
    }
}
