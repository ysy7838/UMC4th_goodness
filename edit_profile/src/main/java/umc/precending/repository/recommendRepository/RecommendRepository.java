package umc.precending.repository.recommendRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.Recommend.Recommend;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend,Long> {
    @Query(value = "select * from recommend order by RAND() limit 3",nativeQuery = true)
    List<Recommend> selectRandom();


}
