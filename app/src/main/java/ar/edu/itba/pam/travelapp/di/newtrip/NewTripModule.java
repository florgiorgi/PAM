package ar.edu.itba.pam.travelapp.di.newtrip;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.TripDao;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.trip.TripRoomRepository;

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
}
