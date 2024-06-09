package sc.skycastmvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Абстрактный класс, предоставляющий общие поля для всех типов
 * климатических данных
 *
 * @see CurrentWeather
 */
@Data
public abstract class Weather {

    @NotNull
    protected String cityName;

    @NotNull
    protected Double lat;

    @NotNull
    protected Double lon;
}
