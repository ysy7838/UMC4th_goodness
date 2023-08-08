package umc.precending.dto.post;

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
public class PostNewsCreateDto {
    @NotBlank(message = "선행을 기록하기 위한 기사 링크를 입력해주세요.")
    private String newsUrl;

    @NotNull(message = "선행을 수행한 연도를 입력해주세요")
    private Integer year;

    @NotNull(message = "선행을 수행한 월을 입력해주세요")
    private Integer month;

    @NotNull(message = "선행을 수행한 일자를 입력해주세요")
    private Integer day;

    @NotNull(message = "선행을 기록하기 위한 카테고리를 입력해주세요.")
    List<Long> categories = new ArrayList<>();

    private boolean type; // 기부 형태, true일 경우 일시적 기부, false일 경우에는 정기 기부

    private Double donation; // 기부 금액
}
