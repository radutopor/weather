package radutopor.weather.webapi;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import radutopor.weather.model.City;
import radutopor.weather.model.DayForecast;

class DayForecastResponse implements WebApiResponse<DayForecast> {
    static final String KEY_CITY = "city";

    @SerializedName("dt")
    long date;

    class Temperature {
        @SerializedName("day")
        float day;
        @SerializedName("night")
        float night;
    }

    class Weather {
        @SerializedName("description")
        String description;
        @SerializedName("icon")
        String iconCode;
    }

    @SerializedName("temp")
    Temperature temperature;

    @SerializedName("weather")
    List<Weather> weatherList;

    @Override
    public DayForecast toModel(Bundle arguments) {
        DayForecast dayForecast = new DayForecast();
        dayForecast.city = (City) arguments.getSerializable(KEY_CITY);
        dayForecast.date = date * 1000; // Date from Web API is in seconds, but I want to store in more conventional millis
        if (temperature != null) {
            dayForecast.temperatureDay = temperature.day;
            dayForecast.temperatureNight = temperature.night;
        }
        if (weatherList != null && !weatherList.isEmpty()) {
            Weather weather = weatherList.get(0);
            dayForecast.weatherDescription = weather.description;
            dayForecast.weatherIconCode = weather.iconCode;
        }
        dayForecast.save();
        return dayForecast;
    }
}
