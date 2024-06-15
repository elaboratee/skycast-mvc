package sc.skycastmvc.misc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChangeForm {

    @NotBlank(message = "⃰ Новый пароль не должен быть пустым")
    @Size(min = 6, max = 16, message = "⃰ Новый пароль должен иметь длину от 6 до 16 знаков")
    private String newPassword;
}
