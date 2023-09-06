package umc.precending.domain.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.category.PostCategory;
import umc.precending.domain.image.PostImage;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class NormalPost extends Post {
    @Lob
    @Column(name = "content", nullable = false)
    private String content; // 개인 게시글 내용

    public NormalPost(String writer, String content, boolean verifiable,
                      int year, int month, int day,
                      List<PostCategory> categoryList, List<PostImage> imageList) {
        this.writer = writer;
        this.content = content;
        this.verifiable = verifiable;
        this.isVerified = false;
        this.year = year;
        this.month = month;
        this.day = day;
        addCategories(categoryList);
        addImages(imageList);
    }

    public void editPost(int year, int month, int day, String content) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.content = content;
    }
}
