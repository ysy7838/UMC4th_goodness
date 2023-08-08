package umc.precending.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberClubSignInDto {
    @NotBlank(message = "로그인을 수행하기 위하여 동아리의 이름을 입력해주세요.")
    @ApiModelProperty(value = "로그인에 필요한 동아리의 이름", example = "test")
    private String name;

    @NotBlank(message = "로그인을 수행하기 위하여 동아리의 유형을 입력해주세요.")
    @ApiModelProperty(value = "로그인에 필요한 동아리의 유형", example = "type")
    private String type;

    @NotBlank(message = "로그인을 수행하기 위하여 학교명을 입력해주세요.")
    @ApiModelProperty(value = "로그인에 필요한 학교명", example = "test")
    private String school;

    @NotBlank(message = "로그인을 수행하기 위한 비밀번호를 입력해주세요.")
    @ApiModelProperty(value = "로그인에 필요한 비밀번호", example = "1234")
    private String password;
}
