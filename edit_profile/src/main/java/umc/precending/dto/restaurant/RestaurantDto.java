package umc.precending.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestaurantDto {
    private String rNum;
    private String name;
    private String adres;
    private String cat;
}
