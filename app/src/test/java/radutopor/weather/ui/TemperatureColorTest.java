package radutopor.weather.ui;

import org.junit.Test;

import radutopor.weather.R;

import static junit.framework.Assert.assertEquals;

public class TemperatureColorTest {
    @Test
    public void forTemperature() throws Exception {
        assertEquals(TemperatureColor.forTemperature(-14).colorResId, R.color.temperature_color_0);
        assertEquals(TemperatureColor.forTemperature(2).colorResId, R.color.temperature_color_1);
        assertEquals(TemperatureColor.forTemperature(20).colorResId, R.color.temperature_color_3);
        assertEquals(TemperatureColor.forTemperature(100000).colorResId, R.color.temperature_color_5);
    }
}