package sc.skycastmvc.misc;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import sc.skycastmvc.model.UserEntity;

@Data
public class RegistrationForm {

    private String username;
    private String password;

    public UserEntity toUserEntity(PasswordEncoder passwordEncoder) {
        return new UserEntity(
                username,
                passwordEncoder.encode(password)
        );
    }
}
