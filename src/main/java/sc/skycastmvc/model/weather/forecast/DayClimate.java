package sc.skycastmvc.model.weather.forecast;

import lombok.Data;
import sc.skycastmvc.model.weather.Condition;

/**
 * Информационный класс, использующийся для хранения усредненных
 * климатических данных в какой-либо день прогноза
 * @see Condition
 */
@Data
public class DayClimate {

    private Double avgtemp_c;

    private Double maxwind_kph;

    private Double totalprecip_mm;

    private Integer avgvis_km;

    private Integer avghumidity;

    private Double daily_chance_of_rain;

    private Double daily_chance_of_snow;

    private Condition condition;
}
