package radutopor.weather.ui;

import org.junit.Test;

import radutopor.weather.R;

import static junit.framework.Assert.assertEquals;

public class WeatherIconTest {
    @Test
    public void fromCode() throws Exception {
        assertEquals(WeatherIcon.fromCode("50d").drawableResId, R.drawable.ic_mist);
        assertEquals(WeatherIcon.fromCode("04n").drawableResId, R.drawable.ic_overcast);
        assertEquals(WeatherIcon.fromCode("11d").drawableResId, R.drawable.ic_storm);
    }
}
