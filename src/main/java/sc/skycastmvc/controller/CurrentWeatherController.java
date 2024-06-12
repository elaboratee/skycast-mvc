package sc.skycastmvc.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/weather/current")
@SessionAttributes("weather")
public class CurrentWeatherController {

    private final WeatherService weatherService;

    public CurrentWeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public String showCurrentWeatherForm() {
        return "current";
    }

    @PostMapping("/get")
    public String processWeather(@NotBlank String cityName,
                                 @ModelAttribute Weather weather,
                                 @AuthenticationPrincipal UserEntity user) {

        JSONObject weatherJson = weatherService.getClimateDataJSON(cityName);
        log.info("User ({}, {}) requested JSON for {}", user.getId(), user.getUsername(), cityName);

        weather.setLocation(weatherService.parseLocation(weatherJson));
        weather.setCurrent(weatherService.parseCurrentClimateData(weatherJson));
        weather.setForecast(weatherService.parseForecastClimateData(weatherJson));

        log.info("User ({}, {}) received object: {} ", user.getId(), user.getUsername(), weather);

        return "redirect:/weather/current";
    }

    @GetMapping("/returnHome")
    public String returnToHomePage(SessionStatus sessionStatus,
                                   @AuthenticationPrincipal UserEntity user) {
        sessionStatus.setComplete();
        log.info("Session completed for user ({}, {}) (redirected to home)", user.getId(), user.getUsername());
        return "redirect:/";
    }

    @GetMapping("/returnWeather")
    public String returnToWeatherPage(SessionStatus sessionStatus,
                                      @AuthenticationPrincipal UserEntity user) {
        sessionStatus.setComplete();
        log.info("Session completed for user ({}, {}) (redirected to weather)", user.getId(), user.getUsername());
        return "redirect:/weather";
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
