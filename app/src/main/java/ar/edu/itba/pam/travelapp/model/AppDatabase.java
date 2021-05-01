package ar.edu.itba.pam.travelapp.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.activity.ActivityDao;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripDao;


@Database(entities = {Trip.class, Activity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    /*
    *    Para iniciar una instancia de la db:
    *    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
    *    AppDatabase.class, "database-name").build();
    *
    * */

    public abstract TripDao tripDao();

    public abstract ActivityDao activityDao();

}
