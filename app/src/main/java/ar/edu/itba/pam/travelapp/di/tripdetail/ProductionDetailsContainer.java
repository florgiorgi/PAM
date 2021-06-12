package ar.edu.itba.pam.travelapp.di.tripdetail;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.activity.ActivityDao;
import ar.edu.itba.pam.travelapp.model.activity.ActivityMapper;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.trip.TripDao;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class ProductionDetailsContainer implements DetailsContainer {
    private final DetailsModule detailsModule;

    private ActivityDao activityDao;
    private ActivityMapper activityMapper;
    private ActivityRepository activityRepository;
    private TripDao tripDao;
    private TripRepository tripRepository;
    private TripMapper tripMapper;
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
    public TripRepository getTripRepository() {
        if (tripRepository == null) {
            this.tripRepository = detailsModule.provideTripRepository(getTripMapper(), getTripDao());
        }
        return tripRepository;
    }

    private TripDao getTripDao() {
        if (tripDao == null) {
            this.tripDao = detailsModule.provideTripDao();
        }
        return tripDao;
    }

    private TripMapper getTripMapper() {
        if (tripMapper == null) {
            this.tripMapper = detailsModule.provideTripMapper();
        }
        return tripMapper;
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
