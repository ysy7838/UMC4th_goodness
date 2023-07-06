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
public class MemberCorporateSignUpDto {
    @NotBlank(message = "회원가입에 필요한 이름을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 기업의 이름", example = "test")
    private String name;

    @NotBlank(message = "회원가입에 필요한 설립일을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 기업의 설립일(연도, 월)", example = "9999/12")
    private String birth;

    @NotBlank(message = "회원가입에 필요한 사업자번호를 입력해주세요.")
    @Length(max = 12)
    @ApiModelProperty(value = "회원가입에 필요한 사업자 번호", example = "123-45-67890")
    private String registrationNumber;

    @NotBlank(message = "회원가입에 필요한 비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호는 최소 8자, 최대 16자로 구성되어야합니다.")
    @ApiModelProperty(value = "회원가입에 필요한 비밀번호", example = "1234")
    private String password;

    @NotBlank(message = "회원가입에 필요한 이메일을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 이메일", example = "test@test.com")
    private String email;
}
