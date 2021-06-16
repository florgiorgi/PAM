package ar.edu.itba.pam.travelapp.model.weather.repository;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.Forecast;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;
import ar.edu.itba.pam.travelapp.model.weather.repository.interfaces.WeatherApiService;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherForecastService extends WeatherApiService {
    @GET("1day/{locationKey}")
    Single<Response<ForecastResponse>> getCurrentForecast(@Path("locationKey") String locationKey,
                                                          @Query("metric") Boolean bool);

    @GET("10day/{locationKey}")
    Single<Response<ForecastResponse>> getFiveDaysForecast(@Path("locationKey") String locationKey,
                                                        @Query("metric") Boolean bool);
}
