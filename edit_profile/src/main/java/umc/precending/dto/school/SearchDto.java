package umc.precending.dto.school;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.school.Search;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchDto {
    @NotBlank(message="동아리 유형(학교 종류)를 입력하세요.")
    @ApiModelProperty(value = "동아리가 속해있는 학교의 종류", example = "대학교")
    private String type;

    @NotBlank(message="학교 이름을 입력하세요.")
    @ApiModelProperty(value = "동아리가 속해있는 학교의 이름", example = "홍익")
    private String keyword;

    public Search toSearch() {
        return new Search(type, keyword);
    }
}
