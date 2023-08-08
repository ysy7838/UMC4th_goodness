package umc.precending.dto.email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class EmailCheckDto {
    @Email
    @NotEmpty(message = "이메일을 입력해 주세요")
    @ApiModelProperty(value = "인증번호가 맞는지 확인하기 위해서 아까 사용자가 입력했던 이메일을 입력해주세요",example = "test@test.com")
    private String email;

    @NotEmpty(message = "인증 번호를 입력해 주세요")
    @ApiModelProperty(value = "인증번호가 맞는지 확인하기 위해서 인증번호를 입력해주세요",example = "XXXXXX")
    private String authNum;

}