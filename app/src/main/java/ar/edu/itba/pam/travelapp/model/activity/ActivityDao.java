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

    @Query("SELECT * FROM ActivityEntity")
    List<ActivityEntity> getAll();

    @Query("SELECT * FROM ActivityEntity as a WHERE a.trip_id = :tripId")
    List<ActivityEntity> findByTripId(long tripId);

    @Query("SELECT * FROM ActivityEntity as a WHERE a.id = :id")
    ActivityEntity findById(long id);

    @Insert
    void insert(ActivityEntity activity);

    @Delete
    void delete(ActivityEntity activity);

    @Update
    void update(ActivityEntity activity);

}
