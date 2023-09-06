package umc.precending.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.Post.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByWriterAndVerifiable(String writer, boolean verifiable);
    List<Post> findAllByWriterAndYearAndMonth(String writer, int year, int month);
    List<Post> findPostsByVerifiableAndIsVerified(boolean verifiable, boolean ifVerified);
}
