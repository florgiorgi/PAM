package ar.edu.itba.pam.travelapp.model.di;

import android.content.Context;

import ar.edu.itba.pam.travelapp.landing.storage.FtuStorage;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class ProductionTripContainer implements TripContainer {
    private final TripModule tripModule;

    private SchedulerProvider schedulerProvider;
    private FtuStorage ftuStorage;
    private TripRepository tripRepository;
    private TripMapper tripMapper;

    public ProductionTripContainer(final Context context) {
        this.tripModule = new TripModule(context);
    }

    @Override
    public Context getApplicationContext() {
        return tripModule.getApplicationContext();
    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        if (schedulerProvider == null) {
            this.schedulerProvider = tripModule.provideScheduler();
        }
        return schedulerProvider;
    }

    @Override
    public FtuStorage getFtuStorage() {
        if (ftuStorage == null) {
            this.ftuStorage = tripModule.provideFtuStorage();
        }
        return ftuStorage;
    }

    @Override
    public TripRepository getTripRepository() {
        if (tripRepository == null) {
            this.tripRepository = tripModule.provideTripRepository();
        }
        return tripRepository;
    }

    private TripMapper getTripMapper() {
        if (tripMapper == null) {
            this.tripMapper = tripModule.provideTripMapper();
        }
        return tripMapper;
    }
}
