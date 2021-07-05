package ar.edu.itba.pam.travelapp.di.newtrip.autocomplete;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherForecastService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherLocationService;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class ProductionAutocompleteContainer implements AutocompleteContainer {
    private final AutocompleteModule autocompleteModule;

    private SchedulerProvider schedulerProvider;
    private WeatherRepository weatherRepository;
    private WeatherLocationService locationService;
    private WeatherForecastService forecastService;

    public ProductionAutocompleteContainer(final Context context) {
        this.autocompleteModule = new AutocompleteModule(context);
    }

    @Override
    public Context getApplicationContext() {
        return autocompleteModule.getApplicationContext();
    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        if (schedulerProvider == null) {
            this.schedulerProvider = autocompleteModule.provideScheduler();
        }
        return schedulerProvider;
    }

    @Override
    public WeatherRepository getWeatherRepository() {
        if (weatherRepository == null) {
            this.weatherRepository = autocompleteModule.provideWeatherDao(getLocationService(), getForecastService());
        }
        return weatherRepository;
    }

    private WeatherForecastService getForecastService() {
        if (forecastService == null) {
            this.forecastService = autocompleteModule.provideForecastService();
        }
        return forecastService;
    }

    private WeatherLocationService getLocationService() {
        if (locationService == null) {
            this.locationService = autocompleteModule.provideLocationService();
        }
        return locationService;
    }
}
