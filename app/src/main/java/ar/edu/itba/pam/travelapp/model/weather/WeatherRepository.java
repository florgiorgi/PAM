package ar.edu.itba.pam.travelapp.model.weather;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface WeatherRepository {
    Flowable<List<City>> findCity(String city);

    Single<City> findFirstMatchCity(String city);

    Single<ForecastResponse> getForecastForCity(String cityKey);

    Single<ForecastResponse> getForecastForCity(City city);
}
