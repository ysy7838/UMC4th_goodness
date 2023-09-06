package umc.precending.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import umc.precending.domain.base.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//내가 추가한것
public class Person_Corporate extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Person_Corporate_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_Id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Corporate_Id")
    private Corporate corporate;

    public static Person_Corporate createPerson_Corporate(Corporate corporate,int score){
        Person_Corporate personCorporate=new Person_Corporate();
        personCorporate.setCorporate(corporate);
        corporate.addScore(score);
        return personCorporate;
    }
}

