package sc.skycastmvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Класс, реализующий асбтрактный класс {@link Weather}.
 * Используется для хранения климатических данных в текущий момент времени <code>dateTime</code>
 * в городе с именем <code>cityName</code>. Время синхронизируется с часовым поясом города <code>tz_id</code>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public final class CurrentWeather extends Weather {

    private String cityName;

    private Date dateTime = new Date();

    private CurrentClimateData current;

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

        int date = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return String.format("%02d.%02d.%02d", date, month, year);
    }
}
