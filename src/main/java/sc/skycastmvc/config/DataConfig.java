package sc.skycastmvc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import sc.skycastmvc.entity.UserEntity;
import sc.skycastmvc.repository.UserRepository;

@Configuration
@Profile("!prod")
public class DataConfig {

    private final PasswordEncoder passwordEncoder;

    public DataConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner loadUsers(UserRepository userRepository) {
        return args -> {
            userRepository.save(new UserEntity("admin", passwordEncoder.encode("qqwwee")));
        };
    }
}
