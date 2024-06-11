package sc.skycastmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import sc.skycastmvc.model.weather.Weather;

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
