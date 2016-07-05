package radutopor.weather.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "Cities")
public class City extends Model implements Serializable {
    @Column(name = "Name")
    public String name;

    @Column(name = "Country")
    public String country;

    public List<DayForecast> getDayForecasts() {
        return getMany(DayForecast.class, DayForecast.CITY);
    }

    public static City get() {
        return new Select().from(City.class).executeSingle();
    }
}
