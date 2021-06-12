package ar.edu.itba.pam.travelapp.di.tripdetail;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.activity.ActivityDao;
import ar.edu.itba.pam.travelapp.model.activity.ActivityMapper;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.repository.WeatherForecastServiceClient;
import ar.edu.itba.pam.travelapp.model.repository.WeatherLocationServiceClient;
import ar.edu.itba.pam.travelapp.model.repository.WeatherRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class ProductionDetailsContainer implements DetailsContainer {
    private final DetailsModule detailsModule;

    private ActivityDao activityDao;
    private ActivityMapper activityMapper;
    private ActivityRepository activityRepository;
    private WeatherRepository weatherRepository;
    private WeatherLocationServiceClient locationServiceClient;
    private WeatherForecastServiceClient forecastServiceClient;
    private SchedulerProvider schedulerProvider;

    public ProductionDetailsContainer(final Context context) {
        this.detailsModule = new DetailsModule(context);
    }

    @Override
    public Context getApplicationContext() {
        return detailsModule.getApplicationContext();
    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        if (schedulerProvider == null) {
            this.schedulerProvider = detailsModule.provideScheduler();
        }
        return schedulerProvider;
    }

    @Override
    public ActivityRepository getActivityRepository() {
        if (activityRepository == null) {
            this.activityRepository = detailsModule
                    .provideActivityRepository(getActivityMapper(), getActivityDao());
        }
        return activityRepository;
    }

    @Override
    public WeatherRepository getWeatherRepository() {
        if (weatherRepository == null) {
            this.weatherRepository = detailsModule.provideWeatherDao(getLocationServiceClient(), getForecastServiceClient());
        }
        return weatherRepository;
    }

    private WeatherLocationServiceClient getLocationServiceClient() {
        if (locationServiceClient == null) {
            this.locationServiceClient = detailsModule.provideLocationClient();
        }
        return locationServiceClient;
    }

    private WeatherForecastServiceClient getForecastServiceClient() {
        if (forecastServiceClient == null) {
            this.forecastServiceClient = detailsModule.provideForecastClient();
        }
        return forecastServiceClient;
    }

    private ActivityDao getActivityDao() {
        if (activityDao == null) {
            this.activityDao = detailsModule.provideActivityDao();
        }
        return activityDao;
    }

    private ActivityMapper getActivityMapper() {
        if (activityMapper == null) {
            this.activityMapper = detailsModule.provideActivityMapper();
        }
        return activityMapper;
    }
}
