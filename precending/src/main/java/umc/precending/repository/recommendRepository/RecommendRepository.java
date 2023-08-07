package umc.precending.repository.recommendRepository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.Recommend.Recommend;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend,Long> {
    @Query(value = "select * from recommend order by RAND() limit 3",nativeQuery = true)
    List<Recommend> selectRandom();

    @Query(value = "select * from recommend r left outer join member_save_recommend msr on msr.recommend_id=r.id and msr.member_id=:memberId where msr.id IS NULL order by RAND() limit 3",nativeQuery = true)
    List<Recommend> selectRandomByMember(@Param("memberId") Long memberId);

}
