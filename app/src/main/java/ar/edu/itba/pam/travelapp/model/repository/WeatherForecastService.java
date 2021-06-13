package ar.edu.itba.pam.travelapp.model.repository;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.forecast.Forecast;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherForecastService extends WeatherApiService {
    @GET("daily/1day/{locationKey}")
    Single<Response<Forecast>> getCurrentForecast(@Path("locationKey") String locationKey);

    @GET("daily/10day/{locationKey}")   // todo check if Single vs Flowable
    Single<Response<List<Forecast>>> getTenDaysForecast(@Path("locationKey") String locationKey);
}
