package ar.edu.itba.pam.travelapp.model.weather.repository;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;
import ar.edu.itba.pam.travelapp.model.weather.repository.interfaces.WeatherApiService;
import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherLocationService extends WeatherApiService {
    @GET("search")
    Flowable<Response<List<City>>> findCity(@Query("q") String city);
}
