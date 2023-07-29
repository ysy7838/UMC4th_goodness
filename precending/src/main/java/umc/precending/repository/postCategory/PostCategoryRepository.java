package umc.precending.repository.postCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.category.PostCategory;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
