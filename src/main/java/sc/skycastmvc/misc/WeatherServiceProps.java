package sc.skycastmvc.misc;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "weather.service")
public class WeatherServiceProps {

    @Value("${weather.service.api-key}")
    private String apiKey;

    @Value("${weather.service.url}")
    private String url;

    @Value("${weather.service.lang}")
    private String lang;
}
