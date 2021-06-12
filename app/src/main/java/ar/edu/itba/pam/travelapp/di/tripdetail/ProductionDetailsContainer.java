package ar.edu.itba.pam.travelapp.di.tripdetail;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.activity.ActivityDao;
import ar.edu.itba.pam.travelapp.model.activity.ActivityMapper;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class ProductionDetailsContainer implements DetailsContainer {
    private final DetailsModule detailsModule;

    private ActivityDao activityDao;
    private ActivityMapper activityMapper;
    private ActivityRepository activityRepository;
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
