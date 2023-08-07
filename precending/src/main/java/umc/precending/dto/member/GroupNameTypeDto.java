package umc.precending.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import umc.precending.domain.member.Member;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupNameTypeDto {
    private String name;
    private String type;
    public GroupNameTypeDto(Member m){
        this.name=m.getName();
        this.type=m.getAuthority().toString();
    }
}
