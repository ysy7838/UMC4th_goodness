package umc.precending.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NewGoodnessDto {
    @NotBlank(message = "관리자는 선행 db에 들어갈 선행을 입력해 주세요")
    @ApiModelProperty(value = "선행 db에 들어갈 선행",example = "나는 오늘 집에서 부모님에게 선물을 하였다.")
    private String good;
}
