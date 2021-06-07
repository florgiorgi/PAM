package ar.edu.itba.pam.travelapp.model.di;

import android.content.Context;

import ar.edu.itba.pam.travelapp.landing.storage.FtuStorage;
import ar.edu.itba.pam.travelapp.landing.storage.SharedPreferencesFTUStorage;
import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.trip.TripRoomRepository;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class TripModule {
    private static final String SP_ID = "travel-buddy-sp";

    private final Context applicationContext;

    /* default */ TripModule(Context context) {
        this.applicationContext = context.getApplicationContext();
    }

    /* default */ Context getApplicationContext() {
        return applicationContext;
    }

    /* default */ SchedulerProvider provideScheduler() {
        return new AndroidSchedulerProvider();
    }

    /* default */ FtuStorage provideFtuStorage() {
        return new SharedPreferencesFTUStorage(
                applicationContext.getSharedPreferences(SP_ID, Context.MODE_PRIVATE));
        // final FtuStorage storage = new SharedPreferencesFTUStorage(sp);  todo check que estemos haciendo esto
    }

    public TripRepository provideTripRepository() {
        return new TripRoomRepository(AppDatabase.getInstance(getApplicationContext()).tripDao(), mapper);
    }

    public TripMapper provideTripMapper() {
    }
}
