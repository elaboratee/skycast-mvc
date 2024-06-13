package sc.skycastmvc.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sc.skycastmvc.model.UserEntity;
import sc.skycastmvc.model.weather.Weather;
import sc.skycastmvc.service.WeatherService;

@Slf4j
@Controller
@RequestMapping("/weather")
@SessionAttributes("weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public String showWeatherForm() {
        return "weather";
    }

    @PostMapping("/get")
    public String processWeather(@NotBlank String cityName,
                                 @ModelAttribute Weather weather,
                                 @AuthenticationPrincipal UserEntity user) {

        log.info("User ({}, {}) requested JSON for {}", user.getId(), user.getUsername(), cityName);

        JSONObject weatherJson;
        try {
            weatherJson = weatherService.getClimateDataJSON(cityName);
        } catch (JSONException e) {
            return "redirect:/error";
        }

        weather.setLocation(weatherService.parseLocation(weatherJson));
        weather.setCurrent(weatherService.parseCurrentClimateData(weatherJson));
        weather.setForecast(weatherService.parseForecastClimateData(weatherJson));

        log.info("User ({}, {}) received object: {} ", user.getId(), user.getUsername(), weather);

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
