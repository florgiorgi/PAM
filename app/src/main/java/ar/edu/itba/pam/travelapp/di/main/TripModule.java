package ar.edu.itba.pam.travelapp.di.main;

import android.content.Context;

import ar.edu.itba.pam.travelapp.landing.storage.FtuStorage;
import ar.edu.itba.pam.travelapp.main.storage.NightModeStorage;
import ar.edu.itba.pam.travelapp.landing.storage.SharedPreferencesFTUStorage;
import ar.edu.itba.pam.travelapp.main.storage.SharedPreferencesNightModeStorage;
import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.TripDao;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.trip.TripRoomRepository;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;

public class TripModule {
    private static final String SP_ID = "travel-buddy-sp";

    private final Context applicationContext;
    private final AppDatabase appDatabase;

    /* default */ TripModule(Context context) {
        this.applicationContext = context.getApplicationContext();
        this.appDatabase = AppDatabase.getInstance(applicationContext);
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
    }

    public NightModeStorage provideNightModeStorage() {
        return new SharedPreferencesNightModeStorage(
                applicationContext.getSharedPreferences(SP_ID, Context.MODE_PRIVATE));
    }

    public TripRepository provideTripRepository(final TripMapper mapper, final TripDao tripDao) {
        return new TripRoomRepository(tripDao, mapper);
    }

    public TripMapper provideTripMapper() {
        return new TripMapper();
    }

    public TripDao provideTripDao() {
        return getAppDatabase().tripDao();
    }

    private AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
