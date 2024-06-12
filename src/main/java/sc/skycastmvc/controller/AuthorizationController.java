package sc.skycastmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import sc.skycastmvc.model.UserEntity;

@Slf4j
@Controller
@RequestMapping("/login")
public class AuthorizationController {

    @GetMapping
    public String login(SessionStatus sessionStatus,
                        @AuthenticationPrincipal UserEntity user) {
        if (user != null) {
            sessionStatus.setComplete();
            log.info("Session completed for user: ({}, {})", user.getId(), user.getUsername());
        }
        return "login";
    }
}
