package radutopor.weather.ui;

import radutopor.weather.R;

public enum WeatherIcon {
    CLEAR_DAY("01d", R.drawable.ic_clear_day),
    CLEAR_NIGHT("01n", R.drawable.ic_clear_night),
    CLOUDS_FEW_DAY("02d", R.drawable.ic_clouds_few_day),
    CLOUDS_FEW_NIGHT("02n", R.drawable.ic_clouds_few_night),
    CLOUDS_DAY("03d", R.drawable.ic_clouds),
    CLOUDS_NIGHT("03n", R.drawable.ic_clouds),
    OVERCAST_DAY("04d", R.drawable.ic_overcast),
    OVERCAST_NIGHT("04n", R.drawable.ic_overcast),
    RAIN_LIGHT_DAY("09d", R.drawable.ic_rain_light),
    RAIN_LIGHT_NIGHT("09n", R.drawable.ic_rain_light),
    RAIN_DAY("10d", R.drawable.ic_rain),
    RAIN_NIGHT("10n", R.drawable.ic_rain),
    STORM_DAY("11d", R.drawable.ic_storm),
    STORM_NIGHT("11n", R.drawable.ic_storm),
    SNOW_DAY("13d", R.drawable.ic_snow),
    SNOW_NIGHT("13n", R.drawable.ic_snow),
    MIST_DAY("50d", R.drawable.ic_mist),
    MIST_NIGHT("50n", R.drawable.ic_mist);

    public String code;
    public int drawableResId;

    WeatherIcon(String code, int drawableResId) {
        this.code = code;
        this.drawableResId = drawableResId;
    }

    public static WeatherIcon fromCode(String code) {
        for (WeatherIcon weatherIcon : values()) {
            if (weatherIcon.code.equals(code)) {
                return weatherIcon;
            }
        }
        return MIST_DAY;
    }
}
