package ar.edu.itba.pam.travelapp.model.weather;

import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;
import io.reactivex.Single;

public interface WeatherRepository {
//    Single<City> findCity(String city) throws Throwable;

    Single<ForecastResponse> getForecastForCity(String cityKey);

//    Single<Forecast> getForecastForCity(City city);
}
