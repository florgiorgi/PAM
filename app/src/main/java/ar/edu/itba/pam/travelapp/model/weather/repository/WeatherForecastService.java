package ar.edu.itba.pam.travelapp.model.weather.repository;

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

    @GET("5day/{locationKey}")
    Single<Response<ForecastResponse>> getFiveDaysForecast(@Path("locationKey") String locationKey,
                                                        @Query("metric") Boolean bool);
}
