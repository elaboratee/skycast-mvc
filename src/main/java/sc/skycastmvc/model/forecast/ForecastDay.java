package sc.skycastmvc.model.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Информационный класс, использующийся для хранения даты <code>date</code>,
 * данных о погоде в указанный день
 * и массива почасовых климатических данных <code>hours</code>
 * @see DayClimate
 * @see ForecastHour
 */
@Data
public class ForecastDay {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private DayClimate day;

    private ForecastHour[] hours;

    public String getDateAndMonthName() {
        int dayOfMonth = date.getDayOfMonth();
        String monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ru"));
        return String.format("%02d %s", dayOfMonth, monthName);
    }
}
