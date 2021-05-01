package ar.edu.itba.pam.travelapp.model.activity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM activity")
    List<Activity> getAll();

    @Query("SELECT * FROM activity WHERE activity.trip_id = :tripId")
    List<Activity> findByTripId(long tripId);

    @Query("SELECT * FROM activity WHERE activity.id = :id")
    Trip findById(long id);

    @Insert
    void insert(Activity activity);

    @Delete
    void delete(Activity activity);

    @Update
    Activity update(Activity activity);

}
