package sc.skycastmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@RequestMapping("/")
@SessionAttributes("weather")
public class HomeController {

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/start")
    public String startUsage() {
        return "redirect:/weather";
    }
}
