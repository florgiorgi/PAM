package ar.edu.itba.pam.travelapp.di.newtrip;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public interface NewTripContainer {

    Context getApplicationContext();

    TripRepository getTripRepository();

    WeatherRepository getWeatherRepository();

    SchedulerProvider getSchedulerProvider();
}
