package sc.skycastmvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import sc.skycastmvc.misc.WeatherServiceProps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class WeatherService {

    private WeatherServiceProps props;

    public WeatherService(WeatherServiceProps props) {
        this.props = props;
    }

    public JSONObject getCurrentWeather(String cityName)
            throws JSONException {

        OkHttpClient httpClient = new OkHttpClient();

        // Формирование URL запроса к API
        String url = props.getUrl() + "/current.json?" +
                "key=" + props.getApiKey() +
                "&q=" + cityName;
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
