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
public class MemberClubSignUpDto {
    @NotBlank(message = "회원가입에 필요한 이름을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 동아리의 이름", name = "test")
    private String name;

    @NotBlank(message = "회원가입에 필요한 동아리 설립일을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 동아리의 설립일(연도, 월)", name = "9999/12")
    private String birth;

    @NotBlank(message = "회원가입에 필요한 동아리 유형을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 동아리의 유형", name = "type")
    private String type;

    @NotBlank(message = "회원가입에 필요한 주소를 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 동아리의 주소", name = "address")
    private String address;

    @NotBlank(message = "회원가입에 필요한 학교명을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 학교명", name = "school")
    private String school;

    @NotBlank(message = "회원가입에 필요한 비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호는 최소 8자, 최대 16자로 구성되어야합니다.")
    @ApiModelProperty(value = "회웝가입에 필요한 비밀번호", name = "1234")
    private String password;

    @NotBlank(message = "회원가입에 필요한 이메일을 입력해주세요.")
    @ApiModelProperty(value = "회원가입에 필요한 이메일", name = "test@test.com")
    private String email;
}
