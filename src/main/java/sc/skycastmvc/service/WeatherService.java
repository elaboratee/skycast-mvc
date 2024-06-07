package sc.skycastmvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import sc.skycastmvc.misc.WeatherServiceProps;
import sc.skycastmvc.model.Weather;

import java.io.IOException;

@Service
@Data
public class WeatherService {

    private final ObjectMapper jacksonObjectMapper;
    private WeatherServiceProps props;

    public WeatherService(WeatherServiceProps props, ObjectMapper jacksonObjectMapper) {
        this.props = props;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    public Weather.CurrentWeather getCurrentWeather(String cityName) {
        // Создаем маппер JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Получаем JSON-объект с климатическими данными
        JSONObject currentWeatherJson = getCurrentWeatherJSON(cityName);

        // Создаем объект, хранящий данные о текущей погоде
        Weather.CurrentWeather currentWeather = new Weather.CurrentWeather();

        try {
            // Выполняем маппинг атрибутов JSON-объекта на поля класса
            currentWeather = objectMapper.readValue(currentWeatherJson.toString(), Weather.CurrentWeather.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return currentWeather;
    }

    private JSONObject getCurrentWeatherJSON(String cityName)
            throws JSONException {

        OkHttpClient httpClient = new OkHttpClient();

        // Формирование URL-запроса к API
        String url = props.getUrl() + "/current.json?" +
                "key=" + props.getApiKey() +
                "&q=" + cityName +
                "&lang=ru";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        // Выполнение запроса
        try (Response response = httpClient.newCall(request).execute()) {
            // Обработка ответа API
            if (response.isSuccessful()) {
                JSONObject json = new JSONObject(response.body().string());
                return json.getJSONObject("current");
            } else {
                throw new JSONException("Weather request failed: " + response.code());
            }
        } catch (IOException e) {
            throw new JSONException("Weather request failed: " + e.getMessage());
        }
    }
}
