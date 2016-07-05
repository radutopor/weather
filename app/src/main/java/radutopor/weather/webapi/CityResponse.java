package radutopor.weather.webapi;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import radutopor.weather.model.City;

class CityResponse implements WebApiResponse<City> {
    @SerializedName("name")
    String name;

    @SerializedName("country")
    String country;

    @Override
    public City toModel(@Nullable Bundle arguments) {
        // For now, we'll only support one primary location, so we're clearing the previous one.
        City previousCity = City.get();
        if (previousCity != null) {
            previousCity.delete();
        }
        City city = new City();
        city.name = name;
        city.country = country;
        city.save();
        return city;
    }
}
