package umc.precending.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostEditDto {
    private Integer year;
    private Integer month;
    private Integer day;
    private String content;
    private List<MultipartFile> files = new ArrayList<>();
    private List<Long> categories = new ArrayList<>();
    private boolean type;
    private Double donation;
}
