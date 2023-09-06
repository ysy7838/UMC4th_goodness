package umc.precending.domain.Recommend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.category.RecommendCategory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "Recommend")
public class Recommend {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goodness;
    public Recommend(String goodness, List<RecommendCategory> categories){
        this.goodness=goodness;
        addCategories(categories);
    }

    @OneToMany(mappedBy = "recommend",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RecommendCategory> recommendCategories=new ArrayList<>();

    public void addCategories(List<RecommendCategory> categories){
        for (RecommendCategory category : categories) {
            category.initRecommend(this);
            this.recommendCategories.add(category);
        }
    }
}

