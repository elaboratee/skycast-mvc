package sc.skycastmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    @NotBlank(message = "City name should not be empty")
    private String cityName;

    @NotNull
    private Date dateTime = new Date();

    @NotNull
    private CurrentWeather climateData;

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
    public static class CurrentWeather {

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
        private String vis_km;
    }
}
