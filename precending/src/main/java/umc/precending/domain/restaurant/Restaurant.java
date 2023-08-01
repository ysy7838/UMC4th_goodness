package umc.precending.domain.restaurant;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Restaurant {
    private int rNum;
    private String name;
    private String adres;
    private String cat;
    private String time;

    public Restaurant() {}

    public Restaurant(int rNum, String name, String adres, String cat, String time) {
        this.rNum = rNum;
        this.name = name;
        this.adres = adres;
        this.cat = cat;
        this.time = time;
    }

    public void setRestaurant(int rNum, String name, String adres, String cat, String time) {
        this.rNum = rNum;
        this.name = name;
        this.adres = adres;
        this.cat = cat;
        this.time = time;

    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
