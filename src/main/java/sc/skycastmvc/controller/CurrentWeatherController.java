package sc.skycastmvc.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
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
                                 @ModelAttribute Weather weather) {

        JSONObject weatherJson = weatherService.getClimateDataJSON(cityName);

        weather.setLocation(weatherService.parseLocation(weatherJson));
        weather.setCurrent(weatherService.parseCurrentClimateData(weatherJson));
        weather.setForecast(weatherService.parseForecastClimateData(weatherJson));
        
        log.info("Weather object: {}", weather);

        return "redirect:/weather/current";
    }

    @GetMapping("/returnHome")
    public String returnToHomePage(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        log.info("Session completed (return to home): {}", sessionStatus);
        return "redirect:/";
    }

    @GetMapping("/returnWeather")
    public String returnToWeatherPage(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        log.info("Session completed (return to weather): {}", sessionStatus);
        return "redirect:/weather";
    }

    @GetMapping("/returnAbout")
    public String returnToAboutUs(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        log.info("Session completed (return to about us): {}", sessionStatus);
        return "redirect:/about";
    }

    @GetMapping("/returnAccount")
    public String returnToAccount(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        log.info("Session completed (return to account): {}", sessionStatus);
        return "redirect:/account";
    }
}
