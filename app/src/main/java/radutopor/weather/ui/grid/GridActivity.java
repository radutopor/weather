package radutopor.weather.ui.grid;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import radutopor.weather.ForecastActivity;
import radutopor.weather.R;
import radutopor.weather.model.City;
import radutopor.weather.ui.TutorialMessage;

public class GridActivity extends ForecastActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_activity);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        scheduleTransition();
        setGrid();
        setForecast();

        TutorialMessage.SWIPE_DOWN.show(this);
    }

    private void scheduleTransition() {
        supportPostponeEnterTransition();
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                supportStartPostponedEnterTransition();
            }
        });
    }

    private void setGrid() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void populateForecast(City city) {
        DayForecastAdapter dayForecastAdapter = new DayForecastAdapter(this, city.getDayForecasts());
        recyclerView.setAdapter(dayForecastAdapter);
    }
}
