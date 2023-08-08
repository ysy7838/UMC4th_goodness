package umc.precending.dto.token;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenRequestDto {
    @NotBlank(message = "토큰 재발급을 위하여 accessToken의 값을 입력해주세요.")
    @ApiModelProperty(value = "accessToken", example = "accessToken")
    private String accessToken;

    @NotBlank(message = "토큰 재발급을 위하여 refreshToken의 값을 입력해주세요.")
    @ApiModelProperty(value = "refreshToken", example = "refreshToken")
    private String refreshToken;
}
