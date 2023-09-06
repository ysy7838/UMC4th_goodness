package umc.precending.domain.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import umc.precending.domain.post.Post;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "postCategory")
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_category_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @Column(nullable = false)
    private Double count;

    public void initPost(Post post) {
        if(this.post == null) this.post = post;
    }

    public void initCategory(Category category) {
        if(this.category == null) this.category = category;
    }

    public void updateCount(double count) {
        this.count += count;
    }

    public PostCategory(Category category) {
        this.category = category;
        this.count = 0.0;
    }

    public PostCategory(RecommendCategory recommendCategory){
        this.category=recommendCategory.getCategory();
        this.count=0.0;
    }
}
