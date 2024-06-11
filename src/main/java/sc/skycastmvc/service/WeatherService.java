package sc.skycastmvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import sc.skycastmvc.misc.WeatherServiceProps;
import sc.skycastmvc.model.*;

import java.io.IOException;

@Service
@Data
@Slf4j
public class WeatherService {

    private final ObjectMapper objectMapper;

    private final WeatherServiceProps props;

    public WeatherService(WeatherServiceProps props, ObjectMapper objectMapper) {
        this.props = props;
        this.objectMapper = objectMapper;
    }

    public JSONObject getCurrentWeatherJSON(String cityName)
            throws JSONException {

        OkHttpClient httpClient = new OkHttpClient();

        // Формирование URL-запроса к API
        String url = props.getUrl() + "/forecast.json?" +
                "key=" + props.getApiKey() +
                "&q=" + cityName +
                "&lang=" + props.getLang() +
                "&days=" + props.getDays();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        // Выполнение запроса
        try (Response response = httpClient.newCall(request).execute()) {

            log.info("Request executed: {}", request);

            // Обработка ответа API
            if (response.isSuccessful()) {
                return new JSONObject(response.body().string());
            } else {
                throw new JSONException("Weather request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new JSONException("Weather request failed: " + e.getMessage());
        }
    }

    public Location parseLocation(JSONObject jsonObject) {

        // Парсинг JSON-объекта "location" из ответа API
        JSONObject locationJson = jsonObject.getJSONObject("location");

        Location location = new Location();
        try {
            // Выполнение маппинга атрибутов JSON-объекта на поля Location
            location = objectMapper.readValue(locationJson.toString(), Location.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return location;
    }

    public CurrentClimateData parseCurrentClimateData(JSONObject jsonObject) {

        // Парсинг JSON-объекта "current" из ответа API
        JSONObject currentJson = jsonObject.getJSONObject("current");

        CurrentClimateData currentClimateData = new CurrentClimateData();
        try {
            // Выполнение маппинга атрибутов JSON-объекта на поля CurrentClimateData
            currentClimateData = objectMapper.readValue(currentJson.toString(), CurrentClimateData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return currentClimateData;
    }

    public ForecastClimateData parseForecastClimateData(JSONObject jsonObject) {

        ForecastClimateData forecastClimateData = new ForecastClimateData();
        try {
            // Парсинг объекта "forecast" из ответа API
            JSONObject forecastJson = jsonObject.getJSONObject("forecast");

            // Парсинг массива объектов "forecastday" из объекта "forecast"
            JSONArray forecastDayJson = forecastJson.getJSONArray("forecastday");

            ForecastDay[] forecastDays = new ForecastDay[forecastDayJson.length()];
            for (int i = 0; i < forecastDayJson.length(); i++) {
                JSONObject dayJson = forecastDayJson.getJSONObject(i);

                ForecastDay forecastDay = new ForecastDay();

                // Парсинг массива объектов "hour"
                JSONArray forecastHourJson = dayJson.getJSONArray("hour");

                ForecastHour[] forecastHours = new ForecastHour[forecastHourJson.length()];
                for (int j = 0; j < forecastHourJson.length(); j++) {
                    JSONObject hour = forecastHourJson.getJSONObject(j);
                    forecastHours[j] = objectMapper.readValue(hour.toString(), ForecastHour.class);
                }
                forecastDays[i] = objectMapper.readValue(dayJson.toString(), ForecastDay.class);
                forecastDays[i].setHours(forecastHours);
            }
            forecastClimateData.setForecastDays(forecastDays);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return forecastClimateData;
    }
}
