package umc.precending.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.member.Club;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NameScoreClubDto {
    private String clubName;
    private int clubScore;

    public NameScoreClubDto(Club c) {
        clubName=c.getName();
        clubScore=c.getScore();
    }
}
