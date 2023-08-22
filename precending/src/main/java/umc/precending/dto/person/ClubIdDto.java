package umc.precending.dto.person;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClubIdDto {
    @NotBlank(message = "점수 주기 위해 필요한 동아리의 이메일을 입력해주세요")
    @ApiModelProperty(value = "동아리의 이메일", example = "club2@test.com")
    private String clubId;
}
