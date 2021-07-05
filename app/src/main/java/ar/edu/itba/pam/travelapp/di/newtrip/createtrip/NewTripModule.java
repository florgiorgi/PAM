package ar.edu.itba.pam.travelapp.di.newtrip.createtrip;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.TripDao;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.trip.TripRoomRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherApiRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherForecastService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherLocationService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherServiceGenerator;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class NewTripModule {
    private final Context applicationContext;
    private final AppDatabase appDatabase;

    /* default */ NewTripModule(Context context) {
        this.applicationContext = context.getApplicationContext();
        this.appDatabase = AppDatabase.getInstance(applicationContext);
    }

    /* default */ Context getApplicationContext() {
        return applicationContext;
    }

    public TripRepository provideTripRepository(final TripMapper mapper, final TripDao tripDao) {
        return new TripRoomRepository(tripDao, mapper);
    }

    public TripMapper provideTripMapper() {
        return new TripMapper();
    }

    public TripDao provideTripDao() {
        return appDatabase.getTripDao();
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

    public SchedulerProvider provideScheduler() {
        return new AndroidSchedulerProvider();
    }
}
