package radutopor.weather.webapi;

public interface Callback<ModelType> {
    void onResponse(ModelType response);

    void onFailure(Throwable problem);
}
