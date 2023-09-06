package umc.precending.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.precending.domain.category.Category;
import umc.precending.dto.category.CategoryResponseDto;
import umc.precending.dto.category.CategorySaveDto;
import umc.precending.exception.category.CategoryDuplicateException;
import umc.precending.exception.category.CategoryNotFoundException;
import umc.precending.repository.category.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

/*
* 배려, 돕기, 기부, 양보, 용기
* */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true) // 데이터베이스에 저장된 모든 카테고리 조회
    public List<CategoryResponseDto> findAll() {
        List<CategoryResponseDto> result = categoryRepository.findAll().stream()
                .map(CategoryResponseDto::toDto).collect(Collectors.toList());

        if(result.isEmpty()) throw new CategoryNotFoundException();

        return result;
    }

    @Transactional // 카테고리 생성
    public void makeCategory(CategorySaveDto saveDto) {
        Category category = getCategory(saveDto);
        categoryRepository.save(category);
    }

    public Category getCategory(CategorySaveDto saveDto) {
        if(categoryRepository.existsCategoryByCategoryName(saveDto.getCategoryName()))
            throw new CategoryDuplicateException();

        return new Category(saveDto.getCategoryName());
    }
}
