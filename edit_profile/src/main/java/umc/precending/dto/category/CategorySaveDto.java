package umc.precending.dto.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategorySaveDto {
    @NotBlank(message = "생성할 카테고리의 이름을 입력해주세요")
    @ApiModelProperty(value = "새롭게 생성할 카테고리 이름", example = "test")
    private String categoryName;
}
