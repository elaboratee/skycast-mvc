package sc.skycastmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import sc.skycastmvc.model.CurrentWeather;

@Controller
@RequestMapping("/weather")
@SessionAttributes("currentWeather")
public class WeatherController {

    @ModelAttribute("currentWeather")
    public CurrentWeather currentWeather() {
        return new CurrentWeather();
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
