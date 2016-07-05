package radutopor.weather.ui.paginated;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.List;

import radutopor.weather.ForecastActivity;
import radutopor.weather.R;
import radutopor.weather.model.City;
import radutopor.weather.model.DayForecast;
import radutopor.weather.ui.TutorialMessage;

public class PaginatedActivity extends ForecastActivity {
    public static final String KEY_PAGE = "page";

    private TextView location;
    private ViewPager viewPager;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paginated_activity);
        location = (TextView) findViewById(R.id.location);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        scheduleTransition();
        initCurrentPage(savedInstanceState);
        setViewPagerListener();
        setForecast();

        TutorialMessage.SWIPE_LEFT.show(this);
    }

    private void scheduleTransition() {
        supportPostponeEnterTransition();
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                supportStartPostponedEnterTransition();
            }
        });
    }

    private void initCurrentPage(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_PAGE)) {
            currentPage = savedInstanceState.getInt(KEY_PAGE, -1);
        } else if (getIntent().hasExtra(KEY_PAGE)) {
            currentPage = getIntent().getIntExtra(KEY_PAGE, -1);
        } else {
            currentPage = 0;
        }
    }

    private void setViewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                TutorialMessage.TAP.show(PaginatedActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                swipeRefreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });
    }

    @Override
    protected void populateForecast(City city) {
        setLocation(city);
        setPages(city.getDayForecasts());
    }

    private void setLocation(City city) {
        String locationString = getString(R.string.location_fmt, city.name, city.country);
        location.setText(locationString);
    }

    private void setPages(List<DayForecast> dayForecasts) {
        DayForecastsPagerAdapter dayForecastsPagerAdapter = new DayForecastsPagerAdapter(getSupportFragmentManager(), dayForecasts);
        viewPager.setAdapter(dayForecastsPagerAdapter);
        viewPager.setCurrentItem(currentPage, false);
    }

    private class DayForecastsPagerAdapter extends FragmentPagerAdapter {
        private List<DayForecast> dayForecasts;

        public DayForecastsPagerAdapter(FragmentManager fm, List<DayForecast> dayForecasts) {
            super(fm);
            this.dayForecasts = dayForecasts;
        }

        @Override
        public int getCount() {
            return dayForecasts.size();
        }

        @Override
        public Fragment getItem(int position) {
            DayForecastFragment dayForecastFragment = new DayForecastFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable(DayForecastFragment.KEY_DAY_FORECAST, dayForecasts.get(position));
            dayForecastFragment.setArguments(arguments);
            return dayForecastFragment;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_PAGE, currentPage);
    }
}
