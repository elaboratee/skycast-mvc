package sc.skycastmvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Condition {

    @NotNull
    private String text;

    @NotNull
    private String icon;
}
