package sc.skycastmvc.model.weather;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sc.skycastmvc.model.weather.current.CurrentClimateData;
import sc.skycastmvc.model.weather.forecast.ForecastClimateData;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Класс, использующийся для хранения климатических данных в текущий момент времени <code>dateTime</code>
 * в городе с именем <code>cityName</code> в поле <code>current</code>.
 * Дата и время синхронизируются с часовым поясом
 * города <code>location.tz_id</code>.
 * <br><br>
 * Также используется для хранения данных почасового и двухдневного прогнозов в поле <code>forecast</code>.
 * @see Location
 * @see CurrentClimateData
 * @see ForecastClimateData
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Weather {

    @NotBlank(message = "Название города не должно быть пустым")
    private String cityName;

    private Date dateTime = new Date();

    private Location location;

    private CurrentClimateData current;

    private ForecastClimateData forecast;

    public String getTime() {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(dateTime);
        calendar.setTimeZone(TimeZone.getTimeZone(getLocation().getTz_id()));

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return String.format("%02d:%02d", hour, minute);
    }

    public String getDate() {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(dateTime);
        calendar.setTimeZone(TimeZone.getTimeZone(getLocation().getTz_id()));

        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        return String.format("%02d.%02d.%04d", date, month, year);
    }
}
