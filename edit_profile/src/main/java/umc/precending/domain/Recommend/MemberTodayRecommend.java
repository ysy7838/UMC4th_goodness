package umc.precending.domain.Recommend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import umc.precending.domain.base.BaseEntity;
import umc.precending.domain.member.Member;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class MemberTodayRecommend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Recommend_ID")
    private Recommend recommend;

    public static MemberTodayRecommend createMemberTodayRecommend(Recommend recommend){
        MemberTodayRecommend memberTodayRecommend=new MemberTodayRecommend();
        memberTodayRecommend.setRecommend(recommend);
        return memberTodayRecommend;
    }

}