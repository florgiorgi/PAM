package ar.edu.itba.pam.travelapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ar.edu.itba.pam.travelapp.model.activity.ActivityDao;
import ar.edu.itba.pam.travelapp.model.activity.ActivityEntity;
import ar.edu.itba.pam.travelapp.model.trip.TripDao;
import ar.edu.itba.pam.travelapp.model.trip.TripEntity;


@Database(entities = {TripEntity.class, ActivityEntity.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static final String DB_NAME = "pm_db";

    public static synchronized AppDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                     .build();
        }
        return instance;
    }

    public abstract TripDao tripDao();
    public abstract ActivityDao activityDao();
}
