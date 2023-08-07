package umc.precending.domain.Recommend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import umc.precending.domain.member.Member;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberSaveRecommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Recommend_ID")
    private Recommend recommend;

    public static MemberSaveRecommend createMemberSaveRecommend(Recommend recommend){
        MemberSaveRecommend membersaveRecommend=new MemberSaveRecommend();
        membersaveRecommend.setRecommend(recommend);
        return membersaveRecommend;
    }
}
