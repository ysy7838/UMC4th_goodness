package umc.precending.repository.recommendRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.recommend.Recommend;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend,Long> {
    @Query(value = "select * from recommend order by RAND() limit 3",nativeQuery = true)
    List<Recommend> selectRandom();

    @Query("select count(r) From Recommend r left join RecommendPost rp on rp.recommend= r and rp.writer=:writer where rp.id IS NULL")
    Long countByRecommendPostAndPerson(String writer);

    @Query("select r from Recommend r Left join RecommendPost rp on rp.recommend= r and rp.writer=:writer where rp.id is NULL")
    List<Recommend> selectRandomByPerson(String writer, Pageable pageable);

}
