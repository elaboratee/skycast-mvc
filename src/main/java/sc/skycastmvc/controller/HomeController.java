package sc.skycastmvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sc.skycastmvc.entity.UserEntity;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(HttpServletRequest request,
                       @AuthenticationPrincipal UserEntity user) {

        String referer = request.getHeader("Referer");
        if (user != null && referer != null && referer.contains("login")) {
            log.info("User authenticated: ({}, {})", user.getId(), user.getUsername());
        }
        return "home";
    }

    @GetMapping("/start")
    public String startUsage() {
        return "redirect:/search";
    }
}
