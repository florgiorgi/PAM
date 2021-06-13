package ar.edu.itba.pam.travelapp.di.tripdetail;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.activity.ActivityDao;
import ar.edu.itba.pam.travelapp.model.activity.ActivityMapper;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.repository.WeatherForecastService;
import ar.edu.itba.pam.travelapp.model.repository.WeatherLocationService;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class ProductionDetailsContainer implements DetailsContainer {
    private final DetailsModule detailsModule;

    private ActivityDao activityDao;
    private ActivityMapper activityMapper;
    private ActivityRepository activityRepository;
    private WeatherRepository weatherRepository;
    private WeatherLocationService locationService;
    private WeatherForecastService forecastService;
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
//            this.weatherRepository = detailsModule.provideWeatherDao(getLocationServiceClient(), getForecastServiceClient());
            this.weatherRepository = detailsModule.provideWeatherDao(getLocationService(), getForecastService());
        }
        return weatherRepository;
    }

    private WeatherLocationService getLocationService() {
        if (locationService == null) {
            this.locationService = detailsModule.provideLocationService();
        }
        return locationService;
    }

    private WeatherForecastService getForecastService() {
        if (forecastService == null) {
            this.forecastService = detailsModule.provideForecastService();
        }
        return forecastService;
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
