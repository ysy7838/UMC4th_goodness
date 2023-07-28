package umc.precending.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.precending.domain.category.Category;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryResponseDto {
    private Long id;
    private String categoryName;

    public static CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(category.getId(), category.getCategoryName());
    }
}
