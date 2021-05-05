package ar.edu.itba.pam.travelapp.model.trip;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trips ORDER BY startDate")
    Flowable<List<TripEntity>> getTrips();

    @Query("SELECT * FROM trips WHERE location LIKE :location")
    Flowable<List<TripEntity>> findByLocation(String location);

    @Query("SELECT * FROM trips WHERE id = :id")
    Flowable<TripEntity> findById(long id);

    @Insert
    void insert(TripEntity trip);

    @Update
    void update(TripEntity trip);

    @Delete
    void delete(TripEntity trip);

}
