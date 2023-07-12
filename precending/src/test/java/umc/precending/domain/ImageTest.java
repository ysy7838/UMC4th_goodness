package umc.precending.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import umc.precending.domain.image.Image;
import umc.precending.domain.image.MemberImage;

@ExtendWith(SpringExtension.class)
public class ImageTest {

    private Image image;

    @BeforeEach
    public void initImage() {
        MultipartFile file = new MockMultipartFile("test.jpeg", "test.jpeg",
                MediaType.IMAGE_JPEG.toString(), "test".getBytes());
        image = new MemberImage(file);
    }

    @Test
    @DisplayName(value = "확장자 추출 메서드 테스트")
    public void extractExtensionTest() {
        //given

        //when
        String extension = image.extractExtension("test.jpeg");

        //then
        Assertions.assertThat(extension).isEqualTo("jpeg");
    }

    @Test
    @DisplayName(value = "저장 이름 생성 메서드 테스트")
    public void generateStoreNameTest() {
        //given

        //when
        String originalName = "test.jpeg";
        String storedName = image.generateStoreName(originalName);

        //then
        Assertions.assertThat(originalName).isNotEqualTo(storedName);
    }

    @Test
    @DisplayName(value = "확장자 유효성 확인 테스트")
    public void checkValidationTest() {
        //given

        //when
        String notValid = "test.txt";
        boolean result = image.checkValidation(notValid);

        //then
        Assertions.assertThat(result).isFalse();
    }
}
