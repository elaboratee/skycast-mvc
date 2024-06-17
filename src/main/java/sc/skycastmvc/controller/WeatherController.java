package sc.skycastmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sc.skycastmvc.entity.UserEntity;
import sc.skycastmvc.exception.CityIsAlreadyFavourite;
import sc.skycastmvc.model.Weather;
import sc.skycastmvc.service.UserService;
import sc.skycastmvc.service.WeatherService;

import java.sql.SQLException;

@Slf4j
@Controller
@RequestMapping("/weather")
@SessionAttributes("weather")
public class WeatherController {

    private final WeatherService weatherService;

    private final UserService userService;

    public WeatherController(WeatherService weatherService, UserService userService) {
        this.weatherService = weatherService;
        this.userService = userService;
    }

    @GetMapping
    public String showWeatherForm() {
        return "weather";
    }

    @PostMapping("/get")
    public String processWeather(@ModelAttribute Weather weather,
                                 @AuthenticationPrincipal UserEntity user) {

        log.info("User ({}, {}) requested JSON for {}",
                user.getId(), user.getUsername(), weather.getCityName());

        JSONObject weatherJson;
        try {
            weatherJson = weatherService.getClimateDataJSON(weather.getCityName());
        } catch (JSONException e) {
            return "redirect:/error";
        }

        weather.setLocation(weatherService.parseLocation(weatherJson));
        weather.setCurrent(weatherService.parseCurrentClimateData(weatherJson));
        weather.setForecast(weatherService.parseForecastClimateData(weatherJson));

        log.info("User ({}, {}) received object: {} ", user.getId(), user.getUsername(), weather);

        return "redirect:/weather";
    }

    @PostMapping("/add_city_to_favourites")
    public String addCityToFavourites(@AuthenticationPrincipal UserEntity user,
                                      @ModelAttribute Weather weather) {

        String cityName = weather.getCityName();
        try {
            userService.addChosenCity(user, cityName);
        } catch (CityIsAlreadyFavourite e) {
            log.error("City {} is already favourite for user ({}, {})", cityName, user.getId(), user.getUsername());
        }

        log.info("User ({}, {}) added city {} to favourites", user.getId(), user.getUsername(), cityName);

        return "redirect:/weather";
    }

    @GetMapping("/returnHome")
    public String returnToHomePage(SessionStatus sessionStatus,
                                   @AuthenticationPrincipal UserEntity user) {
        sessionStatus.setComplete();
        log.info("Session completed for user ({}, {}) (redirected to home)", user.getId(), user.getUsername());
        return "redirect:/";
    }

    @GetMapping("/returnWeather")
    public String returnToSearchPage(SessionStatus sessionStatus,
                                     @AuthenticationPrincipal UserEntity user) {
        sessionStatus.setComplete();
        log.info("Session completed for user ({}, {}) (redirected to search)", user.getId(), user.getUsername());
        return "redirect:/search";
    }

    @GetMapping("/returnAbout")
    public String returnToAboutUs(SessionStatus sessionStatus,
                                  @AuthenticationPrincipal UserEntity user) {
        sessionStatus.setComplete();
        log.info("Session completed for user ({}, {}) (redirected to about)", user.getId(), user.getUsername());
        return "redirect:/about";
    }

    @GetMapping("/returnAccount")
    public String returnToAccount(SessionStatus sessionStatus,
                                  @AuthenticationPrincipal UserEntity user) {
        sessionStatus.setComplete();
        log.info("Session completed for user ({}, {}) (redirected to account)", user.getId(), user.getUsername());
        return "redirect:/account";
    }
}
