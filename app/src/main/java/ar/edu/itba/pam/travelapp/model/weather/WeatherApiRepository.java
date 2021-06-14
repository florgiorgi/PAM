package ar.edu.itba.pam.travelapp.model.weather;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherForecastService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherLocationService;
import ar.edu.itba.pam.travelapp.utils.networking.RetrofitUtils;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class WeatherApiRepository implements WeatherRepository {
    private final WeatherLocationService weatherLocationService;
    private final WeatherForecastService weatherForecastService;

    public WeatherApiRepository(WeatherLocationService weatherLocationService,
                                WeatherForecastService weatherForecastService) {
        this.weatherLocationService = weatherLocationService;
        this.weatherForecastService = weatherForecastService;
    }

    @Override
    public Single<ForecastResponse> getForecastForCity(String cityKey) {
        return RetrofitUtils.performRequest(
                weatherForecastService.getCurrentForecast(cityKey, true));
    }

    @Override
    public Flowable<List<City>> findCity(String city) {
        return RetrofitUtils.performRequest(weatherLocationService.findCity(city));
    }

    @Override
    public Single<City> findFirstMatchCity(String city) {
        return RetrofitUtils.performRequest(weatherLocationService.findCity(city))
                .filter(citiesList -> !citiesList.isEmpty())
                .map(citiesList -> citiesList.get(0)).firstOrError();
    }

    @Override
    public Single<ForecastResponse> getForecastForCity(City city) {
        return RetrofitUtils.performRequest(
                weatherForecastService.getCurrentForecast(city.getKey(), true));
    }
}
