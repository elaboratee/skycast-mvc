package sc.skycastmvc.model.weather.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * Информационный класс, использующийся для хранения даты <code>date</code>
 * и массива почасовых климатических данных <code>hour</code>
 * @see DayClimate
 * @see ForecastHour
 */
@Data
public class ForecastDay {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private DayClimate day;

    private ForecastHour[] hours;
}
