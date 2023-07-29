package umc.precending.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.member.Recommend;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend,Long> {
    @Query(value = "select * from recommend order by RAND() limit 3",nativeQuery = true)
    List<Recommend> selectRandom();
}
