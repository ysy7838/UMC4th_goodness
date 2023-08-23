package umc.precending.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.member.Member;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupShowDto {
    private String name;
    private String url;
    private String introduction;
    public GroupShowDto(Member member){
        this.name=member.getName();
        if(!member.getImages().isEmpty()){
            this.url=member.getImages().get(0).getAccessUrl();
        }
        else{
            this.url="";
        }
        this.introduction=member.getIntroduction();
    }
}
