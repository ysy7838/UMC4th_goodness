package umc.precending.controller.restaurant;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import umc.precending.response.Response;
import umc.precending.service.restaurant.RestaurantService;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/main_home/restaurant")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="가게 목록", notes = "홈화면에 노출될 가게 목록")
    public Response searchResult() {
        return Response.success(restaurantService.setRestaurantList());
    }

    @GetMapping("/main_home/restaurant/info")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="가게 정보", notes = "노출된 가게를 클릭했을 때 나타나는 가게의 정보")
    public Response restaurantInfo(@RequestParam String name) {
        return Response.success(restaurantService.getRestrauntInfo(name));
    }

    @GetMapping("/map_home")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="가게 정보", notes = "노출된 가게를 클릭했을 때 나타나는 가게의 정보")
    public Response mapResult() {
        return Response.success(restaurantService.setRestaurantMapList());
    }
}
