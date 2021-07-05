package ar.edu.itba.pam.travelapp.di.newtrip.autocomplete;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.weather.WeatherApiRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherForecastService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherLocationService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherServiceGenerator;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class AutocompleteModule {
    private final Context applicationContext;

    public AutocompleteModule(Context context) {
        this.applicationContext = context.getApplicationContext();
    }

    /* default */ Context getApplicationContext() {
        return applicationContext;
    }

    public WeatherRepository provideWeatherDao(WeatherLocationService locationService, WeatherForecastService forecastService) {
        return new WeatherApiRepository(locationService, forecastService);
    }

    public WeatherLocationService provideLocationService() {
        return WeatherServiceGenerator.getWeatherLocationService();
    }

    public WeatherForecastService provideForecastService() {
        return WeatherServiceGenerator.getWeatherForecastService();
    }

    /* default */ SchedulerProvider provideScheduler() {
        return new AndroidSchedulerProvider();
    }
}
