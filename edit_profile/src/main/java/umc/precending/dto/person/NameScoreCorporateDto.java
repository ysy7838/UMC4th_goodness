package umc.precending.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.member.Corporate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NameScoreCorporateDto {
    private String corporateName;
    private int corporateScore;

    public NameScoreCorporateDto(Corporate c) {
        corporateName=c.getName();
        corporateScore=c.getScore();
    }
}
