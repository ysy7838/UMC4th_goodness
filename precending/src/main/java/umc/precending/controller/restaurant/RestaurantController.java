package umc.precending.controller.restaurant;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.precending.domain.restaurant.Restaurant;
import umc.precending.service.restaurant.RestaurantService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/profile/restaurant")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="가게 목록", notes = "홈화면에 노출될 가게 목록")
    public List<Restaurant> searchResult() {
        List<Restaurant> restList = restaurantService.setRestaurantList();
        return restList;
    }

    @GetMapping("/profile/restaurant/info")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="가게 정보", notes = "노출된 가게를 클릭했을 때 나타나는 가게의 정보")
    public Restaurant restaurantInfo(@RequestParam String name) {
        return restaurantService.getRestrauntInfo(name);
    }
}
