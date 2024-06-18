package sc.skycastmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sc.skycastmvc.entity.ChosenCity;
import sc.skycastmvc.entity.UserEntity;
import sc.skycastmvc.model.Weather;

import java.net.http.HttpRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/search")
@SessionAttributes("weather")
public class SearchController {

    @ModelAttribute("weather")
    public Weather weather() {
        return new Weather();
    }

    @ModelAttribute("favouriteCities")
    public List<ChosenCity> favouriteCities(@AuthenticationPrincipal UserEntity user) {
        return user.getChosenCities();
    }

    @GetMapping
    public String showSearchForm() {
        return "search";
    }
}
