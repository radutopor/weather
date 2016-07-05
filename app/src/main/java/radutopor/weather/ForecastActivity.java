package radutopor.weather;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import radutopor.weather.model.City;
import radutopor.weather.webapi.Callback;
import radutopor.weather.webapi.WebApi;

public abstract class ForecastActivity extends AppCompatActivity {
    private static final int REQ_PERMISSION_LOCATION = 3816;
    private static final int REQ_ENABLE_LOCATION = 9721;

    private View activityView;
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        activityView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        setSwipeRefreshLayout();
    }

    private void setSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getWebApiForecast();
                }
            });
        }
    }

    protected void setForecast() {
        setDatabaseForecast();
        if (getIntent().getAction() == Intent.ACTION_MAIN && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)) {
            getWebApiForecast();
        }
    }

    private void setDatabaseForecast() {
        City dbCity = City.get();
        if (dbCity != null) {
            populateForecast(dbCity);
        }
    }

    private void getWebApiForecast() {
        if (isInternetAvailable()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(this)
                        .setMessage(R.string.location_permission_msg)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(ForecastActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQ_PERMISSION_LOCATION);
                            }
                        })
                        .setCancelable(false)
                        .show();
            } else {
                getLocationThenWebApiForecast();
            }
        } else {
            Snackbar.make(activityView, R.string.no_internet_msg, Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getLocationThenWebApiForecast() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.location_data_required_msg)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQ_ENABLE_LOCATION);
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                getWebApiForecastForLocation(location);
            } else {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(getString(R.string.location_waiting_msg));
                progressDialog.show();
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        progressDialog.dismiss();
                        getWebApiForecastForLocation(location);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                }, null);
                // I'm only including the following because when I tested on an AVD emulator, it was simply unable (or unwilling) to deliver any location updates.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            Location mockLocation = new Location(LocationManager.NETWORK_PROVIDER);
                            mockLocation.setLatitude(51.5074);
                            mockLocation.setLongitude(0.1278);
                            getWebApiForecastForLocation(mockLocation);
                        }
                    }
                }, 6000);
            }
        }
    }

    private void getWebApiForecastForLocation(Location location) {
        WebApi.getInstance().getForecastDaily(location, new Callback<City>() {
            @Override
            public void onResponse(City city) {
                populateForecast(city);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable problem) {
                Snackbar.make(activityView, R.string.web_api_problem_msg, Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocationThenWebApiForecast();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ENABLE_LOCATION) {
            getLocationThenWebApiForecast();
        }
    }

    protected abstract void populateForecast(City city);
}
