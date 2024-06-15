package sc.skycastmvc.misc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import sc.skycastmvc.annotation.PasswordMatches;
import sc.skycastmvc.annotation.UserAlreadyExists;
import sc.skycastmvc.model.UserEntity;

@Data
@PasswordMatches
@UserAlreadyExists
public class RegistrationForm {

    @NotBlank(message = "⃰ Имя пользователя не должно быть пустым")
    @Size(min = 1, max = 20, message = "⃰ Имя пользователя должно иметь длину от 1 до 20 знаков")
    private String username;

    @NotBlank(message = "⃰ Пароль не должен быть пустым")
    @Size(min = 6, max = 16, message = "⃰ Пароль должен иметь длину от 6 до 16 знаков")
    private String password;

    @NotBlank(message = "⃰ Проверочный пароль не должен быть пустым")
    private String matchingPassword;

    public UserEntity toUserEntity(@NotNull PasswordEncoder passwordEncoder) {
        return new UserEntity(
                username,
                passwordEncoder.encode(password)
        );
    }
}
