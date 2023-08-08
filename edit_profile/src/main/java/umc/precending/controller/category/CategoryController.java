package umc.precending.controller.category;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umc.precending.dto.category.CategorySaveDto;
import umc.precending.response.Response;
import umc.precending.service.category.CategoryService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "카테고리 조회", notes = "데이터베이스에 저장된 모든 카테고리 조회")
    public Response findAll() {
        return Response.success(categoryService.findAll());
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "카테고리 생성", notes = "신규 카테고리를 생성하는 API")
    @ApiImplicitParam(name = "saveDto", value = "카테고리를 생성하기 위한 정보가 담긴 DTO")
    public void makeCategory(@RequestBody @Valid CategorySaveDto saveDto) {
        categoryService.makeCategory(saveDto);
    }
}
