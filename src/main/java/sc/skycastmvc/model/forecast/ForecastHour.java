package sc.skycastmvc.model.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import sc.skycastmvc.model.Condition;

import java.time.LocalDateTime;

/**
 * Информационный класс, использующийся для хранения климатических данных в
 * конкретный час даты <code>time</code>
 * @see Condition
 */
@Data
public class ForecastHour {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;

    private Integer temp_c;

    private Condition condition;

    public String getTime() {
        int hour = time.getHour();
        int minute = time.getMinute();
        return String.format("%02d:%02d", hour, minute);
    }
}
