package umc.precending.domain.member;

import lombok.*;
import umc.precending.domain.base.BaseEntity;
import umc.precending.domain.image.Image;
import umc.precending.domain.image.MemberImage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    protected Long id;

    @Column(name = "name", nullable = false)
    protected String name; // 개인, 동아리, 기업의 이름

    @Column(name = "username", nullable = false)
    protected String username; // 사용자의 아이디

    @Column(name = "birth", nullable = false)
    protected String birth; // 출생/설립 연도

    @Column(name = "password", nullable = false)
    protected String password; // 비밀번호

    @Column(name = "email", nullable = false, unique = true)
    protected String email; // 이메일

    @Column(name = "emailValidation", nullable = false)
    protected boolean emailValidation; // 이메일 인증 여부

    @Column(name = "introduction", nullable = false)
    @Lob
    protected String introduction; // 프로필 화면에서 노출시킬 소개글

    @Enumerated(value = EnumType.STRING)
    protected Authority authority; // 사용자가 어떠한 회원인지를 명시(ex) 개인 회원, 동아리 회원, 기업 회원 등)

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    protected List<MemberImage> images = new ArrayList<>();

    public  void saveImage(List<MemberImage> images) {
        for(MemberImage image : images) {
            image.initMember(this);
            this.images.add(image);
        }
    }
}
