package umc.precending.repository.postCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.Post.Post;
import umc.precending.domain.category.PostCategory;

import java.util.List;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    List<PostCategory> findAllByPost(Post post);
}
