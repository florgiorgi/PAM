package ar.edu.itba.pam.travelapp.model.repository;

import ar.edu.itba.pam.travelapp.model.weather.forecast.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherForecastService {
    @GET("something")
    Call<Forecast> getForecast(String locationKey);
}
