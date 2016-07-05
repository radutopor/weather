package radutopor.weather.ui.paginated;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import radutopor.weather.R;
import radutopor.weather.model.DayForecast;
import radutopor.weather.ui.grid.GridActivity;

import static radutopor.weather.ui.UIUtil.setDayTitle;
import static radutopor.weather.ui.UIUtil.setWeatherIcon;

public class DayForecastFragment extends Fragment {
    public static final String KEY_DAY_FORECAST = "day_forecast";

    private DayForecast dayForecast;
    private ImageView weatherIconImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dayForecast = (DayForecast) getArguments().getSerializable(KEY_DAY_FORECAST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.day_forecast_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setDayTitle((TextView) getView().findViewById(R.id.day_title), dayForecast);
        setWeatherIcon(weatherIconImage = (ImageView) getView().findViewById(R.id.weather_icon), dayForecast);
        setWeatherDescription();
        setTemperatures();
        setTapAction();
    }

    private void setWeatherDescription() {
        TextView weatherDescription = (TextView) getView().findViewById(R.id.weather_description);
        weatherDescription.setText(dayForecast.weatherDescription);
    }

    private void setTemperatures() {
        TextView temperatureDay = (TextView) getView().findViewById(R.id.temperature_day);
        temperatureDay.setText(getString(R.string.temperature_fmt, Math.round(dayForecast.temperatureDay)));

        TextView temperatureNight = (TextView) getView().findViewById(R.id.temperature_night);
        temperatureNight.setText(getString(R.string.temperature_fmt, Math.round(dayForecast.temperatureNight)));
    }

    private void setTapAction() {
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GridActivity.class),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), weatherIconImage, String.valueOf(dayForecast.date)).toBundle());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().finish();
                    }
                }, getResources().getInteger(R.integer.activity_transition_hold_duration));
            }
        });
    }
}
