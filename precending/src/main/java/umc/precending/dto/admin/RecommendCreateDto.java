package umc.precending.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecommendCreateDto {
    @NotBlank(message = "선행을 기록하기 위한 내용을 입력해 주세요")
    private String content;
    @NotNull(message = "선행을 기록하기 위한 카테고리를 입력해주세요")
    private List<Long> categories=new ArrayList<>();
}
