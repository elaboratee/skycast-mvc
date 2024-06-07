package sc.skycastmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CurrentWeather {
        @NotNull
        private String wind_kph;

        @NotNull
        private String temp_c;

        @NotNull
        private String humidity;
    }
}
