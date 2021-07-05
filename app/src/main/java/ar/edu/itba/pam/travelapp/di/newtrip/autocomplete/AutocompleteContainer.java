package ar.edu.itba.pam.travelapp.di.newtrip.autocomplete;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public interface AutocompleteContainer {
    Context getApplicationContext();

    SchedulerProvider getSchedulerProvider();

    WeatherRepository getWeatherRepository();
}
