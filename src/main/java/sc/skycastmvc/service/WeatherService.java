package sc.skycastmvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import sc.skycastmvc.misc.WeatherServiceProps;
import sc.skycastmvc.model.CurrentClimateData;
import sc.skycastmvc.model.Location;

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

    public JSONObject getCurrentWeatherJSON(String cityName)
            throws JSONException {

        OkHttpClient httpClient = new OkHttpClient();

        // Формирование URL-запроса к API
        String url = props.getUrl() + "/current.json?" +
                "key=" + props.getApiKey() +
                "&q=" + cityName +
                "&lang=" + props.getLang();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        // Выполнение запроса
        try (Response response = httpClient.newCall(request).execute()) {
            // Обработка ответа API
            if (response.isSuccessful()) {
                JSONObject json = new JSONObject(response.body().string());
//                return json.getJSONObject("current");
                return json;
            } else {
                throw new JSONException("Weather request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new JSONException("Weather request failed: " + e.getMessage());
        }
    }
}
