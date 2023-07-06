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
public class Corporate extends Member {
    @Column(name = "registrationNumber", nullable = false, unique = true)
    private String registrationNumber; // 사업자 등록 번호

    public Corporate(String name, String birth, String password,
                     String email, String registrationNumber) {
        this.name = name;
        this.username = email;
        this.birth = birth;
        this.password = password;
        this.email = email;
        this.emailValidation = false;
        this.introduction = "";
        this.registrationNumber = registrationNumber;
        this.authority = Authority.ROLE_CORPORATE;
    }
}