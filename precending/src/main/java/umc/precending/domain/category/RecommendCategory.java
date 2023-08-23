package umc.precending.domain.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.Recommend.Recommend;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecommendCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_category_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_id")
    private Recommend recommend;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void initRecommend(Recommend recommend){
        if(this.recommend==null) this.recommend=recommend;
    }
    public void initCategory(Category category){
        if(this.category==null) this.category=category;
    }
    public RecommendCategory(Category category){
        this.category=category;
    }
}