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
public class Person extends Member{
    @Column(name = "phone", nullable = false, unique = true)
    private String phone; // 개인 회원의 핸드폰 번호

    @Column(name = "phoneValidation", nullable = false)
    private boolean phoneValidation; // 개인 회원의 핸드폰 인증 여부

    public Person(String name, String birth, String password,
                  String email, String phone) {
        this.name = name;
        this.username = email;
        this.birth = birth;
        this.password = password;
        this.email = email;
        this.emailValidation = false;
        this.introduction = "";
        this.phone = phone;
        this.phoneValidation = false;
        this.authority = Authority.ROLE_PERSON;
    }
}
