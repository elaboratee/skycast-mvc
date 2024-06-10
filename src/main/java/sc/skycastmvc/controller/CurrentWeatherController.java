package sc.skycastmvc.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sc.skycastmvc.model.CurrentWeather;
import sc.skycastmvc.service.WeatherService;

@Slf4j
@Controller
@RequestMapping("/weather/current")
@SessionAttributes("currentWeather")
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
    public String processCurrentWeather(@NotBlank String cityName,
                                        @ModelAttribute CurrentWeather currentWeather) {

        JSONObject currentWeatherJson = weatherService.getCurrentWeatherJSON(cityName);

        currentWeather.setLocation(weatherService.parseLocation(currentWeatherJson));
        currentWeather.setCurrent(weatherService.parseCurrentClimateData(currentWeatherJson));
        
        log.info("CurrentWeather object: {}", currentWeather);

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
}
