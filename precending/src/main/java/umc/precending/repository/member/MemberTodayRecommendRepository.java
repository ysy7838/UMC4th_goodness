package umc.precending.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.member.MemberTodayRecommend;

import java.util.List;

public interface MemberTodayRecommendRepository extends JpaRepository<MemberTodayRecommend,Long> {

    List<MemberTodayRecommend> findByMember_Id(Long id);

    @Query("select mtr from MemberTodayRecommend mtr join fetch mtr.member m join fetch mtr.recommend r where m.username=:username")
    List<MemberTodayRecommend> findByMemberName(String username);


}
