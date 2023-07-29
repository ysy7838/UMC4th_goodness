package umc.precending.domain.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.category.PostCategory;
import umc.precending.domain.image.PostImage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsPost extends Post {
    @Column(name = "newsUrl", nullable = false)
    private String newsUrl; // 뉴스에 접근하기 위한 뉴스 링크

    @Column(name = "content", nullable = false)
    @Lob
    private String content; // 뉴스 본문

    public NewsPost(String writer, String newsUrl, String content,
                    int year, int month, int day,
                    List<PostCategory> categories, List<PostImage> images) {
        this.writer = writer;
        this.verifiable = true;
        this.isVerified = false;
        this.newsUrl = newsUrl;
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
        addCategories(categories);
        addImages(images);
    }

    private void updateNews(String newsUrl, String content) {
        this.newsUrl = newsUrl;
        this.content = content;
    }

    public void editPost(int year, int month, int day, String newsUrl, String content) {
        this.year = year;
        this.month = month;
        this.day = day;
        updateNews(newsUrl, content);
    }

    public void editPost(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
