package sc.skycastmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sc.skycastmvc.model.Weather;

@Controller
@RequestMapping("/weather")
@SessionAttributes("weather")
public class WeatherController {

    @ModelAttribute("weather")
    public Weather weather() {
        return new Weather();
    }

    @GetMapping
    public String showWeatherForm() {
        return "weather";
    }

    @GetMapping("/returnHome")
    public String returnToHomePage(SessionStatus sessionStatus) {
        return "redirect:/";
    }
}
