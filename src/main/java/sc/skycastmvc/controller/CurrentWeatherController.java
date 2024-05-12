package sc.skycastmvc.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sc.skycastmvc.model.Weather;
import sc.skycastmvc.service.WeatherService;

@Slf4j
@Controller
@RequestMapping("/weather/current")
@SessionAttributes("weather")
public class CurrentWeatherController {

    private WeatherService weatherService;

    public CurrentWeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public String showCurrentWeatherForm() {
        return "current";
    }

    @PostMapping("/get")
    public String processCurrentWeather(@NotBlank String cityName,
                                        @ModelAttribute Weather weather) {
        weather.setCityName(cityName);
        weather.setClimateData(weatherService.getCurrentWeather(cityName));
        return "redirect:/weather/current";
    }

    @GetMapping("/returnHome")
    public String returnToHomePage(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        log.info("Session status (return to home): {}", sessionStatus);
        return "redirect:/";
    }

    @GetMapping("/returnWeather")
    public String returnToWeatherPage(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        log.info("Session status (return to weather): {}", sessionStatus);
        return "redirect:/weather";
    }
}
