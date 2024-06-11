package sc.skycastmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

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
}
