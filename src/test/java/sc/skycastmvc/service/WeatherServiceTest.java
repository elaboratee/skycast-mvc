package sc.skycastmvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import sc.skycastmvc.misc.WeatherServiceProps;
import sc.skycastmvc.model.Location;
import sc.skycastmvc.model.current.CurrentClimateData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class WeatherServiceTest {

    @Mock
    private WeatherServiceProps props;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private WeatherService sut;

    @BeforeEach
    public void restoreState() {
        MockitoAnnotations.openMocks(this);
        sut = new WeatherService(props, objectMapper);
    }

    @Test
    void canParseLocation() throws JSONException, JsonProcessingException {
        // given
        String locationString = "{\"name\": \"Moscow\", \"lat\": 55.75, \"lon\": 37.62, \"tz_id\": \"Europe\"}";
        JSONObject locationJson = new JSONObject(locationString);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("location", locationJson);

        Location expectedLocation = new Location();
        expectedLocation.setName("Moscow");
        expectedLocation.setLat("55.75");
        expectedLocation.setLon("37.62");
        expectedLocation.setTz_id("Europe");

        given(objectMapper.readValue(locationJson.toString(), Location.class)).willReturn(expectedLocation);

        // when
        Location parsedLocation = sut.parseLocation(jsonObject);

        // then
        assertEquals(expectedLocation, parsedLocation);
    }

    @Test
    void locationCanNotBeParsed() throws JSONException, JsonProcessingException {
        // given
        String locationString = "{\"name\": \"Moscow\", \"lat\": 55.75, \"lon\": 37.62, \"tz_id\": \"Europe/Moscow\"}";
        JSONObject locationJson = new JSONObject(locationString);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("location", locationJson);

        given(objectMapper.readValue(locationJson.toString(), Location.class))
                .willThrow(new JsonProcessingException("Exception") {});

        // when
        Location parsedLocation = sut.parseLocation(jsonObject);

        // then
        assertNull(parsedLocation.getName());
        assertNull(parsedLocation.getLat());
        assertNull(parsedLocation.getLon());
        assertNull(parsedLocation.getTz_id());
    }

    @Test
    void canParseCurrentClimateData() throws JSONException, JsonProcessingException {
        // given
        String currentString = "{\"temp_c\": 20.0, \"wind_kph\": 10.0}, \"humidity\": 57}";
        JSONObject currentJson = new JSONObject(currentString);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("current", currentJson);

        CurrentClimateData expectedCurrentClimateData = new CurrentClimateData();
        expectedCurrentClimateData.setTemp_c(20);
        expectedCurrentClimateData.setWind_kph("10.0");
        expectedCurrentClimateData.setHumidity("57");

        given(objectMapper.readValue(currentJson.toString(), CurrentClimateData.class))
                .willReturn(expectedCurrentClimateData);

        // when
        CurrentClimateData currentClimateData = sut.parseCurrentClimateData(jsonObject);

        // then
        assertEquals(expectedCurrentClimateData, currentClimateData);
    }

    @Test
    void currentClimateDataCanNotBeParsed() throws JSONException, JsonProcessingException {
        // given
        String currentString = "{\"temp_c\": 20.0, \"wind_kph\": 10.0}, \"humidity\": 57}";
        JSONObject currentJson = new JSONObject(currentString);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("current", currentJson);

        given(objectMapper.readValue(currentJson.toString(), CurrentClimateData.class))
                .willThrow(new JsonProcessingException("Exception") {});

        // when
        CurrentClimateData expectedCurrentClimateData = sut.parseCurrentClimateData(jsonObject);

        // then
        assertNull(expectedCurrentClimateData.getTemp_c());
        assertNull(expectedCurrentClimateData.getWind_kph());
        assertNull(expectedCurrentClimateData.getHumidity());
    }
}
