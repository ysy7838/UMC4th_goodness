package umc.precending.dto.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateRequestDto {
    private String name;
    private String birth;
    private String introduction;
}
