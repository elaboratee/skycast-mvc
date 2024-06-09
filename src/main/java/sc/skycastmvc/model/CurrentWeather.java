package sc.skycastmvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Класс, реализующий асбтрактный класс {@link Weather}.
 * Используется для хранения климатических данных в текущий момент времени <code>dateTime</code>
 * в городе с именем <code>cityName</code>. Время синхронизируется с часовым поясом города <code>tz_id</code>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public final class CurrentWeather extends Weather {

    @NotNull
    private Date dateTime = new Date();

    @NotNull
    private String tz_id;

    @NotNull
    private CurrentClimateData climateData;

    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hour, minute);
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        int date = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return String.format("%02d.%02d.%02d", date, month, year);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CurrentClimateData {
        @NotNull
        private Integer temp_c;

        @NotNull
        private Condition condition;

        @NotNull
        private String wind_kph;

        @NotNull
        private String wind_dir;

        @NotNull
        private Integer pressure_in;

        @NotNull
        private String humidity;

        @NotNull
        private String cloud;

        @NotNull
        private Integer feelslike_c;

        @NotNull
        private String precip_mm;

        @NotNull
        private String gust_kph;

        @NotNull
        private String vis_km;
    }
}
