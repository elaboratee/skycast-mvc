package sc.skycastmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sc.skycastmvc.model.UserEntity;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    @ModelAttribute("user")
    public UserEntity user(@AuthenticationPrincipal UserEntity user) {
        return user;
    }

    @GetMapping
    public String account(SessionStatus status) {
        return "account";
    }
}
