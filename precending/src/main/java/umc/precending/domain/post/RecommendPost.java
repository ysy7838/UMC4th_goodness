package umc.precending.domain.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.recommend.PersonTodayRecommend;
import umc.precending.domain.recommend.Recommend;
import umc.precending.domain.category.PostCategory;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RecommendPost extends Post{
    @Column(name = "content",nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_id")
    private Recommend recommend;

    public RecommendPost(PersonTodayRecommend personTodayRecommend){
        this.recommend=personTodayRecommend.getRecommend();
        this.writer=personTodayRecommend.getPerson().getUsername();
        this.content=personTodayRecommend.getRecommend().getGoodness();
        this.verifiable=false;
        this.isVerified=false;
        this.year= LocalDate.now().getYear();
        this.month=LocalDate.now().getMonthValue();
        this.day=LocalDate.now().getDayOfMonth();
        addCategories(personTodayRecommend.getRecommend().getRecommendCategories().stream().map(c->new PostCategory(c)).collect(Collectors.toList()));
    }
}
