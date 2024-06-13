package sc.skycastmvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sc.skycastmvc.model.UserEntity;

@Controller
@RequestMapping("/logout")
@Slf4j
public class LogoutController {

    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         Authentication authentication,
                         @AuthenticationPrincipal UserEntity user) {
        logoutHandler.logout(request, response, authentication);
        log.info("Logout successful for user ({}, {})", user.getId(), user.getUsername());
        return "redirect:/";
    }
}
