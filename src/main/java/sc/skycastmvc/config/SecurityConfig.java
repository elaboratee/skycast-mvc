package sc.skycastmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sc.skycastmvc.model.UserEntity;
import sc.skycastmvc.repository.UserRepository;

import java.beans.BeanProperty;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            UserEntity user = userRepository.findByUsername(username);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/weather/**").access("hasRole('USER')")
                        .requestMatchers("/", "/start").access("permitAll()"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/"))
                .build();
    }
}
