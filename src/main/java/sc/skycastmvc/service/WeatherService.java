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
import org.springframework.stereotype.Service;
import sc.skycastmvc.misc.WeatherServiceProps;
import sc.skycastmvc.model.weather.Location;
import sc.skycastmvc.model.weather.current.CurrentClimateData;
import sc.skycastmvc.model.weather.forecast.DayClimate;
import sc.skycastmvc.model.weather.forecast.ForecastClimateData;
import sc.skycastmvc.model.weather.forecast.ForecastDay;
import sc.skycastmvc.model.weather.forecast.ForecastHour;

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

    public JSONObject getClimateDataJSON(String cityName)
            throws JSONException {

        OkHttpClient httpClient = new OkHttpClient();

        // Формирование URL-запроса к API
        String url = props.getUrl() + "/forecast.json?" +
                "key=" + props.getApiKey() +
                "&q=" + cityName +
                "&lang=" + props.getLang() +
                "&days=" + "8";

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
                log.error("Request failed: {}", response.code());
                throw new JSONException("Request failed: " + response.code());
            }
        } catch (IOException e) {
            log.error("Request can not be executed: {}", request);
            throw new JSONException("Request can not be executed: " + e.getMessage());
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
            log.error("Location can not be parsed: {}", locationJson);
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
            log.error("CurrentClimateData can not be parsed: {}", currentJson);
        }
        return currentClimateData;
    }

    public ForecastClimateData parseForecastClimateData(JSONObject jsonObject) {

        // Парсинг объекта "forecast" из ответа API
        JSONObject forecastJson = jsonObject.getJSONObject("forecast");

        ForecastClimateData forecastClimateData = new ForecastClimateData();
        try {

            // Парсинг массива объектов "forecastday" из объекта "forecast"
            JSONArray forecastDayJson = forecastJson.getJSONArray("forecastday");

            ForecastDay[] forecastDays = new ForecastDay[forecastDayJson.length()];
            for (int i = 0; i < forecastDayJson.length(); i++) {
                JSONObject dayJson = forecastDayJson.getJSONObject(i);
                JSONObject dayClimateJson = dayJson.getJSONObject("day");

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
                forecastDays[i].setDay(objectMapper.readValue(dayClimateJson.toString(), DayClimate.class));
            }
            forecastClimateData.setForecastDays(forecastDays);

        } catch (JsonProcessingException e) {
            log.error("ForecastClimateData can not be parsed: {}", forecastJson);
        }

        return forecastClimateData;
    }
}
