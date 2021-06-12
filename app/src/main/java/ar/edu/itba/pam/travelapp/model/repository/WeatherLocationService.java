package ar.edu.itba.pam.travelapp.model.repository;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.location.City;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherLocationService {
    @GET("search")
    Call<List<City>> findCity(@Query("apikey") String apiKey, @Query("q") String city);
}
