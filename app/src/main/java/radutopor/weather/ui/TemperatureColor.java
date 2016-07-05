package radutopor.weather.ui;

import radutopor.weather.R;

public enum TemperatureColor {
    TEMPERATURE_0(0, R.color.temperature_color_0),
    TEMPERATURE_1(8, R.color.temperature_color_1),
    TEMPERATURE_2(14, R.color.temperature_color_2),
    TEMPERATURE_3(20, R.color.temperature_color_3),
    TEMPERATURE_4(29, R.color.temperature_color_4),
    TEMPERATURE_5(Integer.MAX_VALUE, R.color.temperature_color_5);

    public int maxTemperature;
    public int colorResId;

    TemperatureColor(int maxTemperature, int colorResId) {
        this.maxTemperature = maxTemperature;
        this.colorResId = colorResId;
    }

    public static TemperatureColor forTemperature(int temperature) {
        for (TemperatureColor temperatureColor : values()) {
            if (temperature <= temperatureColor.maxTemperature) {
                return temperatureColor;
            }
        }
        return null;
    }
}
