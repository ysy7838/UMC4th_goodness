package umc.precending.repository.recommendRepository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.Recommend.Recommend;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend,Long> {
    @Query(value = "select * from recommend order by RAND() limit 3",nativeQuery = true)
    List<Recommend> selectRandom();

    @Query(value = "select * from recommend r left outer join person_save_recommend psr on psr.recommend_id=r.id and psr.person_id=:personId where psr.id IS NULL order by RAND() limit 3",nativeQuery = true)
    List<Recommend> selectRandomByPerson(@Param("personId") Long personId);

}
