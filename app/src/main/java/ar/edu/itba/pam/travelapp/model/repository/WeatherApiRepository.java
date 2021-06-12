package ar.edu.itba.pam.travelapp.model.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Optional;

import ar.edu.itba.pam.travelapp.model.weather.forecast.Forecast;
import ar.edu.itba.pam.travelapp.model.weather.location.City;

public class WeatherApiRepository implements WeatherRepository {
    private final WeatherLocationServiceClient weatherLocationService;
    private final WeatherForecastServiceClient weatherForecastService;

    public WeatherApiRepository(WeatherLocationServiceClient weatherLocationService,
                                WeatherForecastServiceClient weatherForecastService) {
        this.weatherLocationService = weatherLocationService;
        this.weatherForecastService = weatherForecastService;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Optional<City> findCity(String city) throws Throwable {
        List<City> cities = weatherLocationService.findCity(city);
        for (City c: cities) {
            if (c != null) {
                if (city.equals(c.getEnglishName()) || city.equals(c.getLocalizedName())) {
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Forecast getForecastForCity(String cityKey) {
        return weatherForecastService.getForecast(cityKey);
    }

    @Override
    public Forecast getForecastForCity(City city) {
        return weatherForecastService.getForecast(city.getKey());
    }
}
