package ar.edu.itba.pam.travelapp.model.activity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM activities")
    Flowable<List<ActivityEntity>> getActivities();

    @Query("SELECT * FROM activities WHERE trip_id = :tripId")
    Flowable<List<ActivityEntity>> findByTripId(long tripId);

    @Query("SELECT * FROM activities WHERE id = :id")
    Flowable<ActivityEntity> findById(long id);

    @Insert
    void insert(ActivityEntity activity);

    @Delete
    void delete(ActivityEntity activity);

    @Update
    void update(ActivityEntity activity);

}
