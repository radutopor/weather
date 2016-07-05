package radutopor.weather.ui.grid;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import radutopor.weather.R;
import radutopor.weather.model.DayForecast;
import radutopor.weather.ui.paginated.PaginatedActivity;

import static radutopor.weather.ui.UIUtil.setDayTitle;
import static radutopor.weather.ui.UIUtil.setWeatherIcon;

class DayForecastAdapter extends RecyclerView.Adapter<DayForecastAdapter.ViewHolder> {
    private List<DayForecast> dayForecasts;
    private Activity activity;
    private LayoutInflater layoutInflater;

    public DayForecastAdapter(Activity activity, List<DayForecast> dayForecasts) {
        this.activity = activity;
        this.dayForecasts = dayForecasts;
        layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getItemCount() {
        return dayForecasts.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.day_forecast_grid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bindItem(dayForecasts.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dayTitle;
        private ImageView weatherIconImage;

        public ViewHolder(View itemView) {
            super(itemView);
            dayTitle = (TextView) itemView.findViewById(R.id.day_title);
            weatherIconImage = (ImageView) itemView.findViewById(R.id.weather_icon);
            setOnItemClickListener();
        }

        private void setOnItemClickListener() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String transitionName = String.valueOf(dayForecasts.get(getAdapterPosition()).date);
                    ActivityCompat.startActivity(activity, new Intent(activity, PaginatedActivity.class)
                                    .putExtra(PaginatedActivity.KEY_PAGE, getAdapterPosition()),
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, weatherIconImage, transitionName).toBundle());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activity.finish();
                        }
                    }, activity.getResources().getInteger(R.integer.activity_transition_hold_duration));
                }
            });
        }

        public void bindItem(DayForecast dayForecast) {
            setDayTitle(dayTitle, dayForecast);
            setWeatherIcon(weatherIconImage, dayForecast);
        }
    }
}
