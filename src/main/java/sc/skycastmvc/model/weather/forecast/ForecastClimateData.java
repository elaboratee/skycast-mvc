package sc.skycastmvc.model.weather.forecast;

import lombok.Data;

/**
 * Информационный класс, использующийся для хранения климатических данных из
 * дневного прогноза в поле <code>forecastDay</code>
 * @see ForecastDay
 */
@Data
public class ForecastClimateData {

    private ForecastDay[] forecastDays;
}
