package umc.precending.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.Post.NormalPost;
import umc.precending.dto.category.CategoryResponseDto;
import umc.precending.dto.image.ImageDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostNormalResponseDto {
    private Long id;
    private String content;
    private LocalDateTime firstCreatedDate;
    private LocalDateTime lastModifiedDate;
    private boolean verifiable;
    private boolean isVerified;
    private List<ImageDto> images = new ArrayList<>();
    private List<String> categories = new ArrayList<>();

    public PostNormalResponseDto(NormalPost post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.firstCreatedDate = post.getFirstCreatedDate();
        this.lastModifiedDate = post.getLastModifiedDate();
        this.verifiable = post.isVerifiable();
        this.isVerified = post.isVerified();
        this.images = post.getImageList().stream()
                .map(ImageDto::toDto).collect(Collectors.toList());
        this.categories = post.getCategoryList().stream()
                .map(value -> value.getCategory().getCategoryName())
                .collect(Collectors.toList());
    }
}
