package umc.precending.domain.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends Member {

    @Column(name = "school", nullable = false)
    private String school; // 동아리가 속해있는 학교

    @Column(name = "address", nullable = false)
    private String address; // 동아리 주소

    @Column(name = "clubType", nullable = false)
    private String type; // 동아리 유형

    @Column(name="score",nullable=false)
    private int score;//추가했다

    public void addScore(int score){
        this.score+=score;
    }

    public void cancelScore(int score){this.score-=score;}

    public void resetScore(){
        this.score=0;
    }

    public Club(String name, String birth, String password,
                String email, String type, String school, String address) {
        this.name = name;
        this.username = email;
        this.birth = birth;
        this.password = password;
        this.email = email;
        this.emailValidation = false;
        this.introduction = "";
        this.authority = Authority.ROLE_CLUB;
        this.school = school;
        this.address = address;
        this.type = type;
        this.score=0;
    }

}
