package ar.edu.itba.pam.travelapp.model.repository;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.location.City;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherLocationService extends WeatherApiService {
    @GET("search")
    Flowable<List<City>> findCity(@Query("apikey") String apiKey, @Query("q") String city);
}
