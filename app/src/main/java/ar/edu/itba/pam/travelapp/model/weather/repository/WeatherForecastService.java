package ar.edu.itba.pam.travelapp.model.weather.repository;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.Forecast;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;
import ar.edu.itba.pam.travelapp.model.weather.repository.interfaces.WeatherApiService;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherForecastService extends WeatherApiService {
    @GET("1day/{locationKey}")
    Single<Response<ForecastResponse>> getCurrentForecast(@Path("locationKey") String locationKey);

    @GET("10day/{locationKey}")   // todo check if Single vs Flowable
    Single<Response<List<Forecast>>> getTenDaysForecast(@Path("locationKey") String locationKey);
}
