package ar.edu.itba.pam.travelapp.di.tripdetail;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.activity.ActivityDao;
import ar.edu.itba.pam.travelapp.model.activity.ActivityMapper;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRoomRepository;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherForecastService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherLocationService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherServiceGenerator;
import ar.edu.itba.pam.travelapp.model.weather.WeatherApiRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class DetailsModule {
    private final Context applicationContext;
    private final AppDatabase appDatabase;

    /* default */ DetailsModule(Context context) {
        this.applicationContext = context.getApplicationContext();
        this.appDatabase = AppDatabase.getInstance(applicationContext);
    }

    /* default */ Context getApplicationContext() {
        return applicationContext;
    }

    /* default */ SchedulerProvider provideScheduler() {
        return new AndroidSchedulerProvider();
    }

    public ActivityRepository provideActivityRepository(final ActivityMapper mapper, final ActivityDao activityDao) {
        return new ActivityRoomRepository(activityDao, mapper);
    }

    public ActivityMapper provideActivityMapper() {
        return new ActivityMapper();
    }

    public ActivityDao provideActivityDao() {
        return appDatabase.getActivityDao();
    }

    public WeatherRepository provideWeatherDao(WeatherLocationService locationService,
                                               WeatherForecastService forecastService) {
        return new WeatherApiRepository(locationService, forecastService);
    }

    public WeatherLocationService provideLocationService() {
        return WeatherServiceGenerator.getWeatherLocationService();
    }

    public WeatherForecastService provideForecastService() {
        return WeatherServiceGenerator.getWeatherForecastService();
    }
}
