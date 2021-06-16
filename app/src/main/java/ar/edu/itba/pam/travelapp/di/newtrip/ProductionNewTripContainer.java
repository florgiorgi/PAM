package ar.edu.itba.pam.travelapp.di.newtrip;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.trip.TripDao;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherForecastService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherLocationService;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class ProductionNewTripContainer implements NewTripContainer {
    private final NewTripModule newTripModule;

    private TripRepository tripRepository;
    private TripMapper tripMapper;
    private TripDao tripDao;
    private WeatherRepository weatherRepository;
    private WeatherLocationService locationService;
    private WeatherForecastService forecastService;
    private SchedulerProvider schedulerProvider;

    public ProductionNewTripContainer(Context context) {
        this.newTripModule = new NewTripModule(context);
    }

    @Override
    public Context getApplicationContext() {
        return newTripModule.getApplicationContext();
    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        if (schedulerProvider == null) {
            this.schedulerProvider = newTripModule.provideScheduler();
        }
        return schedulerProvider;
    }

    @Override
    public TripRepository getTripRepository() {
        if (tripRepository == null) {
            this.tripRepository = newTripModule.provideTripRepository(getTripMapper(), getTripDao());
        }
        return tripRepository;
    }

    @Override
    public WeatherRepository getWeatherRepository() {
        if (weatherRepository == null) {
            this.weatherRepository = newTripModule.provideWeatherDao(getLocationService(), getForecastService());
        }
        return weatherRepository;
    }

    private WeatherLocationService getLocationService() {
        if (locationService == null) {
            this.locationService = newTripModule.provideLocationService();
        }
        return locationService;
    }

    private WeatherForecastService getForecastService() {
        if (forecastService == null) {
            this.forecastService = newTripModule.provideForecastService();
        }
        return forecastService;
    }

    private TripDao getTripDao() {
        if (tripDao == null) {
            this.tripDao = newTripModule.provideTripDao();
        }
        return tripDao;
    }

    private TripMapper getTripMapper() {
        if (tripMapper == null) {
            this.tripMapper = newTripModule.provideTripMapper();
        }
        return tripMapper;
    }
}
