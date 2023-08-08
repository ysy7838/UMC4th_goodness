package umc.precending.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsCategoryByCategoryName(String categoryName);
}
