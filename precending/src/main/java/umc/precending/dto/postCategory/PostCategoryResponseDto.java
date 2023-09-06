package umc.precending.dto.postCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.category.Category;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostCategoryResponseDto {
    private Long id;
    private String categoryName;
    private double value;

    public PostCategoryResponseDto(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.value = 0.0;
    }

    public void addValue(double value) {
        this.value += value;
    }
}
