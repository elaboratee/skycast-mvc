package sc.skycastmvc.model;

import lombok.Data;

/**
 * Информационный класс, хранящий данные о географическом
 * местоположении города и его часовом поясе
 */
@Data
public class Location {

    private String name;

    private String lat;

    private String lon;

    private String tz_id;
}
