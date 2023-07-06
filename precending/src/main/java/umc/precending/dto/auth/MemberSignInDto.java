package umc.precending.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberSignInDto {
    @NotBlank(message = "로그인을 수행하기 위한 아이디를 입력해주세요.")
    @ApiModelProperty(value = "로그인에 필요한 아이디", example = "test")
    private String username;

    @NotBlank(message = "로그인을 수행하기 위한 비밀번호를 입력해주세요.")
    @ApiModelProperty(value = "로그인에 필요한 비밀번호", example = "1234")
    private String password;

    public UsernamePasswordAuthenticationToken getAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
