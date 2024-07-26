package sc.skycastmvc.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sc.skycastmvc.misc.PasswordChangeForm;
import sc.skycastmvc.entity.UserEntity;
import sc.skycastmvc.service.UserService;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("username")
    public String user(@AuthenticationPrincipal UserEntity user) {
        return user.getUsername();
    }

    @ModelAttribute("passwordChangeForm")
    public PasswordChangeForm passwordChangeForm() {
        return new PasswordChangeForm();
    }

    @GetMapping
    public String account() {
        return "account";
    }

    @PostMapping("/change_password")
    public String changePassword(@Valid PasswordChangeForm form,
                                 @AuthenticationPrincipal UserEntity user,
                                 Errors errors) {

        if (errors.hasErrors()) {
            return "account";
        }

        userService.changePassword(user.getUsername(), form.getNewPassword());

        log.info("User ({}, {}) changed password to {}", user.getId(),
                user.getUsername(),
                form.getNewPassword());

        // Сбрасываем аутентификацию, чем вызываем выход пользователя из системы
        SecurityContextHolder.getContext().setAuthentication(null);

        log.info("Logout successful for user ({}, {})", user.getId(), user.getUsername());

        return "redirect:/login";
    }
}
