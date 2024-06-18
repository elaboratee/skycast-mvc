package sc.skycastmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @PostMapping("/quit_error")
    public String quitError(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }
}
