package radutopor.weather.webapi;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import radutopor.weather.model.City;
import radutopor.weather.model.DayForecast;

class ForecastDailyResponse implements WebApiResponse<City> {
    @SerializedName("city")
    CityResponse cityResponse;

    @SerializedName("list")
    List<DayForecastResponse> dayForecastResponses;

    @Override
    public City toModel(@Nullable Bundle arguments) {
        City city = cityResponse.toModel(arguments);

        List<DayForecast> dayForecasts = new ArrayList();
        arguments = arguments == null ? new Bundle() : arguments;
        arguments.putSerializable(DayForecastResponse.KEY_CITY, city);
        for (DayForecastResponse dayForecastResponse : dayForecastResponses) {
            dayForecasts.add(dayForecastResponse.toModel(arguments));
        }

        return city;
    }
}
