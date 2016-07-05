package radutopor.weather.ui;

import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import radutopor.weather.R;
import radutopor.weather.model.DayForecast;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UIUtilTest {
    @Mock
    DayForecast dayForecast;

    @Mock
    TextView dayTitle;

    @Test
    public void setDayTitle() throws Exception {
        Calendar calendar = Calendar.getInstance();
        dayForecast.date = calendar.getTimeInMillis();
        UIUtil.setDayTitle(dayTitle, dayForecast);
        verify(dayTitle).setText(R.string.today);

        calendar.roll(Calendar.DAY_OF_MONTH, true);
        dayForecast.date = calendar.getTimeInMillis();
        UIUtil.setDayTitle(dayTitle, dayForecast);
        verify(dayTitle).setText(R.string.tomorrow);
    }
}
