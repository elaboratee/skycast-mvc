package sc.skycastmvc.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Информационный класс, хранящий данные о географическом
 * местоположении города и его часовом поясе
 * @see Weather
 */
@Data
public class Location {
    private String name;

    private String lat;

    private String lon;

    private String tz_id;
}
