package sc.skycastmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sc.skycastmvc.model.weather.Weather;

@Slf4j
@Controller
@RequestMapping("/weather")
@SessionAttributes("weather")
public class WeatherController {

    @ModelAttribute("weather")
    public Weather Weather() {
        return new Weather();
    }

    @GetMapping
    public String showWeatherForm() {
        return "weather";
    }

    @GetMapping("/returnHome")
    public String returnToHomePage() {
        return "redirect:/";
    }
}
