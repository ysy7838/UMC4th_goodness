package umc.precending.domain.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.base.BaseEntity;
import umc.precending.domain.category.PostCategory;
import umc.precending.domain.image.PostImage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Post")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    protected Long id;

    @Column(name = "writer", nullable = false)
    protected String writer; // 게시글 작성자

    @Column(name = "verifiable", nullable = false)
    protected boolean verifiable; // 인증 가능 여부

    @Column(name = "isVerified", nullable = false)
    protected boolean isVerified; // 인증 여부 - 관리자가 게시글을 인증하였는지를 확인

    @Column(name = "year", nullable = false)
    protected Integer year; // 선행을 수행한 연도

    @Column(name = "month", nullable = false)
    protected Integer month; // 선행을 수행한 월

    @Column(name = "day", nullable = false)
    protected Integer day; // 선행을 수행한 일자

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "post")
    protected List<PostImage> imageList = new ArrayList<>(); // 게시글에 첨부되는 이미지

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    protected List<PostCategory> categoryList = new ArrayList<>(); // 게시글이 속한 카테고리

    protected void addCategories(List<PostCategory> categories) {
        for(PostCategory category : categories) {
            category.initPost(this);
            this.categoryList.add(category);
        }
    }

    protected void addImages(List<PostImage> images) {
        for(PostImage image : images) {
            image.initImage(this);
            this.imageList.add(image);
        }
    }

    public void editImages(List<PostImage> images) {
        imageList.removeAll(this.imageList);
        addImages(images);
    }

    public void editCategories(List<PostCategory> categories) {
        categoryList.removeAll(this.categoryList);
        addCategories(categories);
    }

    public void verifyPost() {
        this.isVerified = true;
    }
}
