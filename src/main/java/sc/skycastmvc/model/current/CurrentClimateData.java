package sc.skycastmvc.model.current;

import lombok.Data;
import sc.skycastmvc.model.Condition;
import sc.skycastmvc.model.Weather;

/**
 * Информационный класс, использующийся для хранения текущих климатических данных
 * @see Weather
 */
@Data
public class CurrentClimateData {

    private Integer temp_c;

    private Condition condition;

    private String wind_kph;

    private String wind_dir;

    private Integer pressure_in;

    private String humidity;

    private String cloud;

    private Integer feelslike_c;

    private String precip_mm;

    private String gust_kph;

    private String vis_km;
}
