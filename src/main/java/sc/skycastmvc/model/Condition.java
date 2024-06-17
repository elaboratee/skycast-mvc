package sc.skycastmvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информационный класс, испольщуюшийся для хранения вспомогательных данных
 * о климате, таких как описание погоды и соответствующая ему иконка
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Condition {

    @NotNull
    private String text;

    @NotNull
    private String icon;
}
