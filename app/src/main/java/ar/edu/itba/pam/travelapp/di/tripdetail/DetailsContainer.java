package ar.edu.itba.pam.travelapp.di.tripdetail;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public interface DetailsContainer {
    Context getApplicationContext();

    SchedulerProvider getSchedulerProvider();

    ActivityRepository getActivityRepository();
}
