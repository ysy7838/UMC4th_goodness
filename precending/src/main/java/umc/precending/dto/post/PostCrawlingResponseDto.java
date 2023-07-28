package umc.precending.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostCrawlingResponseDto {
    private String content; // 기사 본문 내용
    private String accessUrl; // 기사에 첨부된 이미지
}
