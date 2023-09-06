package umc.precending.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.post.NewsPost;
import umc.precending.domain.post.NormalPost;
import umc.precending.domain.post.Post;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostResponseDto<T> {
    private T responseData;

    public PostResponseDto toDto(Post post) {
        if(post instanceof NormalPost) return new PostResponseDto(new PostNormalResponseDto((NormalPost) post));
        return new PostResponseDto(new PostNewsResponseDto((NewsPost)post));
    }
}
