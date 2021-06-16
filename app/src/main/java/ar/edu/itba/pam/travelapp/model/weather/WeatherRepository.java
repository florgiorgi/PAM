package ar.edu.itba.pam.travelapp.model.weather;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface WeatherRepository {
    Flowable<List<City>> findCity(String city);

    Single<City> findFirstMatchCity(String city);

    Single<ForecastResponse> getForecastForCityForOneDay(String cityKey);

    Single<ForecastResponse> getForecastForCityForFiveDays(String cityKey);
}
