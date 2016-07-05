package radutopor.weather.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "DayForecasts")
public class DayForecast extends Model implements Serializable {
    static final String CITY = "City";

    @Column(name = CITY, onDelete = Column.ForeignKeyAction.CASCADE)
    public City city;

    @Column(name = "Date")
    public long date;

    @Column(name = "TemperatureDay")
    public float temperatureDay;

    @Column(name = "TemperatureNight")
    public float temperatureNight;

    @Column(name = "WeatherDescription")
    public String weatherDescription;

    @Column(name = "WeatherIconCode")
    public String weatherIconCode;
}
