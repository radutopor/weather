package radutopor.weather.webapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface WebApiInterface {
    @GET("forecast/daily")
    Call<ForecastDailyResponse> getForecastDaily(@Query("lat") double latitude, @Query("lon") double longitude, @Query("units") String units,
                                                 @Query("cnt") int daysCount, @Query("appid") String appId);
}
