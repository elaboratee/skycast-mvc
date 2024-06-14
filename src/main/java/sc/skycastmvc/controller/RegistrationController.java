package sc.skycastmvc.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import sc.skycastmvc.misc.RegistrationForm;
import sc.skycastmvc.model.UserEntity;
import sc.skycastmvc.repository.UserRepository;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("registrationForm")
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }

    @GetMapping
    public String register(SessionStatus sessionStatus,
                           @AuthenticationPrincipal UserEntity user) {
        if (user != null) {
            sessionStatus.setComplete();
            log.info("Session completed for user ({}, {})", user.getId(), user.getUsername());
        }
        return "register";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm form,
                                      Errors errors) {

        if (errors.hasErrors()) {
            return "register";
        }

        userRepository.save(form.toUserEntity(passwordEncoder));
        log.info("User registered successfully: {}", form.toString());
        return "redirect:/login";
    }
}
