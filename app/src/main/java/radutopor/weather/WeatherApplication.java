package radutopor.weather;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import radutopor.weather.webapi.WebApi;

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        WebApi.initialize(getString(R.string.openweathermap_appid));
    }
}
