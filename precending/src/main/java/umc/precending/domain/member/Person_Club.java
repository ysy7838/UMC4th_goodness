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
public class Person_Club extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Person_Club_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_Id")
    private Person person;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Club_Id")
    private Club club;

    public static Person_Club createPerson_Club(Club club,int score){
        Person_Club personClub=new Person_Club();
        personClub.setClub(club);
        club.addScore(score);
        return personClub;
    }
    public void cancel(){

    }
}