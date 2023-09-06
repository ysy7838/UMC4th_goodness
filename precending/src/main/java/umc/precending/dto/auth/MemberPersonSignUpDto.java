package umc.precending.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberPersonSignUpDto {
    @NotBlank(message = "회원가입에 필요한 이름을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 사용자의 이름", example = "test")
    private String name;

    @NotBlank(message = "회원가입에 필요한 생년월일을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 사용자의 생년월일", example = "99991231")
    private String birth;

    @NotBlank(message = "회원가입에 필요한 이메일을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 사용자의 이메일", example = "test@test.com")
    private String email;

    @NotBlank(message = "회원가입에 필요한 비밀번호를 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 사용자의 비밀번호", example = "1234")
    @Length(min = 8, max = 16, message = "비밀번호는 최소 8자, 최대 16자로 구성되어야합니다.")
    private String password;
}
