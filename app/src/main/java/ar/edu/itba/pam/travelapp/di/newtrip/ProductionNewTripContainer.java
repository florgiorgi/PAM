package ar.edu.itba.pam.travelapp.di.newtrip;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.trip.TripDao;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;

public class ProductionNewTripContainer implements NewTripContainer {
    private final NewTripModule newTripModule;

    private TripRepository tripRepository;
    private TripMapper tripMapper;
    private TripDao tripDao;

    public ProductionNewTripContainer(Context context) {
        this.newTripModule = new NewTripModule(context);
    }

    @Override
    public Context getApplicationContext() {
        return newTripModule.getApplicationContext();
    }

    @Override
    public TripRepository getTripRepository() {
        if (tripRepository == null) {
            this.tripRepository = newTripModule.provideTripRepository(getTripMapper(), getTripDao());
        }
        return tripRepository;
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
