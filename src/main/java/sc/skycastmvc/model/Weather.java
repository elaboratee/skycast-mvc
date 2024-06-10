package sc.skycastmvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Абстрактный класс, предоставляющий общие поля для всех типов
 * климатических данных. В частности, в климатические
 * данные включется информация о местоположении и названии конкретного города
 * @see CurrentWeather
 * @see Location
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Weather {

    private Location location;
}
