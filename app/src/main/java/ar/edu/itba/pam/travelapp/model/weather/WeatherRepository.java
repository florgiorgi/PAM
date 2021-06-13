package ar.edu.itba.pam.travelapp.model.weather;

import ar.edu.itba.pam.travelapp.model.weather.forecast.Forecast;
import ar.edu.itba.pam.travelapp.model.weather.location.City;
import io.reactivex.Single;

public interface WeatherRepository {
//    Single<City> findCity(String city) throws Throwable;

    Single<Forecast> getForecastForCity(String cityKey);

//    Single<Forecast> getForecastForCity(City city);
}
