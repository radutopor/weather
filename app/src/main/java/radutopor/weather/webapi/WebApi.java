package radutopor.weather.webapi;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import radutopor.weather.model.City;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebApi {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final int DAYS_COUNT = 5;
    private static final String UNITS = "metric";

    private static WebApi instance;

    public static void initialize(String appId) {
        instance = new WebApi(appId);
    }

    public static WebApi getInstance() {
        if (instance == null) {
            throw new IllegalStateException("initialize() not called");
        }
        return instance;
    }

    private String appId;
    private WebApiInterface webApiInterface;

    private WebApi(String appId) {
        this.appId = appId;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webApiInterface = retrofit.create(WebApiInterface.class);
    }

    public void getForecastDaily(Location location, Callback<City> callback) {
        Call<ForecastDailyResponse> call = webApiInterface.getForecastDaily(location.getLatitude(), location.getLongitude(), UNITS, DAYS_COUNT, appId);
        call.enqueue(new WebApiCallback<ForecastDailyResponse, City>(callback, null));
    }

    /**
     * This adapts Web API response types to model types which are used throwout the rest of the app.
     *
     * @param <ResponseType> The expected Web API response type
     * @param <ModelType>    The model type which ResponseType converts to
     */
    public class WebApiCallback<ResponseType extends WebApiResponse<ModelType>, ModelType> implements retrofit2.Callback<ResponseType> {
        private Callback<ModelType> callback;
        private Bundle toModelArguments;

        public WebApiCallback(Callback<ModelType> callback, @Nullable Bundle toModelArguments) {
            this.callback = callback;
            this.toModelArguments = toModelArguments;
        }

        @Override
        public void onResponse(Call<ResponseType> call, retrofit2.Response<ResponseType> response) {
            ModelType model = response.body().toModel(toModelArguments);
            callback.onResponse(model);
        }

        @Override
        public void onFailure(Call<ResponseType> call, Throwable problem) {
            callback.onFailure(problem);
        }
    }
}
