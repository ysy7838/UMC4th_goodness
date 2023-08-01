package umc.precending.domain.restaurant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Image {
    String link;

    public Image() { }
    public Image(String link) { this.link = link; }
}
