package umc.precending.domain.Recommend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import umc.precending.domain.member.Member;
import umc.precending.domain.member.Person;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonSaveRecommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_ID")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Recommend_ID")
    private Recommend recommend;

    public static PersonSaveRecommend createPersonSaveRecommend(Recommend recommend){
        PersonSaveRecommend personSaveRecommend=new PersonSaveRecommend();
        personSaveRecommend.setRecommend(recommend);
        return personSaveRecommend;
    }
}
