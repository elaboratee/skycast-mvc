package sc.skycastmvc.misc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import sc.skycastmvc.model.UserEntity;

@Data
public class RegistrationForm {

    @NotBlank(message = "Invalid username or password")
    private String username;

    @NotBlank(message = "Invalid username or password")
    @Size(min = 6, max = 16, message = "Invalid username or password")
    private String password;

    public UserEntity toUserEntity(PasswordEncoder passwordEncoder) {
        return new UserEntity(
                username,
                passwordEncoder.encode(password)
        );
    }
}
