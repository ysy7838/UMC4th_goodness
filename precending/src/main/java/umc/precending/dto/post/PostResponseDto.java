package umc.precending.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.Post.NewsPost;
import umc.precending.domain.Post.NormalPost;
import umc.precending.domain.Post.Post;

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
