package umc.precending.domain.recommend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import umc.precending.domain.base.BaseEntity;
import umc.precending.domain.member.Person;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class PersonTodayRecommend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_ID")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Recommend_ID")
    private Recommend recommend;

    public static PersonTodayRecommend createMemberTodayRecommend(Recommend recommend){
        PersonTodayRecommend personTodayRecommend =new PersonTodayRecommend();
        personTodayRecommend.setRecommend(recommend);
        return personTodayRecommend;
    }
}