package radutopor.weather.ui;

import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import radutopor.weather.R;
import radutopor.weather.model.DayForecast;

public class UIUtil {
    private static final SimpleDateFormat DAY_TITLE_FORMAT = new SimpleDateFormat("EEEE");

    public static void setDayTitle(TextView dayTitle, DayForecast dayForecast) {
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(dayForecast.date);
        Calendar now = Calendar.getInstance();
        if (day.get(Calendar.YEAR) == now.get(Calendar.YEAR) && day.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            dayTitle.setText(R.string.today);
        } else if (day.get(Calendar.YEAR) == now.get(Calendar.YEAR) && day.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR) == 1) {
            dayTitle.setText(R.string.tomorrow);
        } else {
            dayTitle.setText(DAY_TITLE_FORMAT.format(day.getTime()));
        }
    }

    public static void setWeatherIcon(ImageView weatherIconImage, DayForecast dayForecast) {
        WeatherIcon weatherIcon = WeatherIcon.fromCode(dayForecast.weatherIconCode);
        weatherIconImage.setImageResource(weatherIcon.drawableResId);

        TemperatureColor temperatureColor = TemperatureColor.forTemperature(Math.round(dayForecast.temperatureDay));
        int tintColor = weatherIconImage.getContext().getResources().getColor(temperatureColor.colorResId);
        weatherIconImage.setColorFilter(tintColor);

        ViewCompat.setTransitionName(weatherIconImage, String.valueOf(dayForecast.date));
    }
}
