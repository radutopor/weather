package radutopor.weather.webapi;

import android.os.Bundle;
import android.support.annotation.Nullable;

public interface WebApiResponse<ModelType> {
    ModelType toModel(@Nullable Bundle arguments);
}
