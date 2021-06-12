package ar.edu.itba.pam.travelapp.model.repository;

import java.util.Optional;

import ar.edu.itba.pam.travelapp.model.weather.forecast.Forecast;
import ar.edu.itba.pam.travelapp.model.weather.location.City;

public interface WeatherRepository {
    Optional<City> findCity(String city) throws Throwable;

    Forecast getForecastForCity(String cityKey);

    Forecast getForecastForCity(City city);
}
