package umc.precending.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.post.RecommendPost;

public interface RecommendPostRepository extends JpaRepository<RecommendPost,Long> {
    boolean existsByRecommendIdAndWriter(Long recommendId,String writer);
    void deleteByRecommendIdAndWriter(Long recommendId,String writer);
}
