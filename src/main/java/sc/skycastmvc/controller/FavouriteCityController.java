package sc.skycastmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sc.skycastmvc.entity.UserEntity;
import sc.skycastmvc.exception.CityIsAlreadyFavourite;
import sc.skycastmvc.exception.CityIsNotFavourite;
import sc.skycastmvc.model.Weather;
import sc.skycastmvc.service.UserService;

@Controller
@RequestMapping("/favourite_city")
@Slf4j
@SessionAttributes("weather")
public class FavouriteCityController {

    private final UserService userService;

    public FavouriteCityController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addCityToFavourites(@AuthenticationPrincipal UserEntity user,
                                      @ModelAttribute Weather weather) {

        String cityName = weather.getLocation().getName();
        try {
            userService.addChosenCity(user, cityName);
        } catch (CityIsAlreadyFavourite e) {
            log.error("City {} is already favourite for user ({}, {})", cityName, user.getId(), user.getUsername());
        }

        log.info("User ({}, {}) added city {} to favourites", user.getId(), user.getUsername(), cityName);

        return "redirect:/weather";
    }

    @PostMapping("/delete")
    public String deleteCityFromFavourites(@AuthenticationPrincipal UserEntity user,
                                           @RequestParam("cityToDelete") String cityToDelete) {

        try {
            userService.deleteChosenCity(user, cityToDelete);
        } catch (CityIsNotFavourite e) {
            log.error("City {} is not favourite for user ({}, {})", cityToDelete, user.getId(), user.getUsername());
        }

        log.info("User ({}, {}) deleted city {}", user.getId(), user.getUsername(), cityToDelete);

        return "redirect:/search";
    }
}
