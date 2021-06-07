package ar.edu.itba.pam.travelapp.model.di;

import android.content.Context;

import ar.edu.itba.pam.travelapp.landing.storage.FtuStorage;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public interface TripContainer {
    Context getApplicationContext();

    SchedulerProvider getSchedulerProvider();

    FtuStorage getFtuStorage();

    TripRepository getTripRepository();
}
