package sc.skycastmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWeather() throws Exception {
        mockMvc.perform(get("/weather"))
                .andExpect(status().isOk())
                .andExpect(view().name("weather"));
    }
}
