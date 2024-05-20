package sc.skycastmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        log.info("Session completed (login page): {}", sessionStatus);
        return "login";
    }
}
