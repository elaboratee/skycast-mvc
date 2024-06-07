package sc.skycastmvc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sc.skycastmvc.model.UserEntity;
import sc.skycastmvc.repository.UserRepository;

@Configuration
public class DataConfig {

    @Bean
    public CommandLineRunner loadUsers(UserRepository userRepository) {
        return args -> {
            userRepository.save(new UserEntity("temp", "0000"));
        };
    }
}
