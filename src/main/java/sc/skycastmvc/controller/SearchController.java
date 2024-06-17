package sc.skycastmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sc.skycastmvc.entity.ChosenCity;
import sc.skycastmvc.entity.UserEntity;
import sc.skycastmvc.model.Weather;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/search")
@SessionAttributes("weather")
public class SearchController {

    @ModelAttribute("weather")
    public Weather weather() {
        return new Weather();
    }

    @ModelAttribute("favouriteCities")
    public List<ChosenCity> favouriteCities(@AuthenticationPrincipal UserEntity user) {
        return user.getChosenCities();
    }

    @GetMapping
    public String showSearchForm() {
        return "search";
    }

    @PostMapping("/favourite_city")
    public String favouriteCity(@ModelAttribute Weather weather,
                                @RequestParam("favouriteCityName") String favouriteCityName) {
        weather.setCityName(favouriteCityName);
        return "redirect:/weather/get";
    }
}
