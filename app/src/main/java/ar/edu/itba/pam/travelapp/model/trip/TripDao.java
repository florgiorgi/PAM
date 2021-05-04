package ar.edu.itba.pam.travelapp.model.trip;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trips")
    List<TripEntity> getAll();

    @Query("SELECT * FROM trips WHERE location LIKE :location")
    List<TripEntity> findByLocation(String location);

    @Query("SELECT * FROM trips WHERE id = :id")
    Trip findById(long id);

    @Insert
    void insert(TripEntity trip);

    @Update
    void update(TripEntity trip);

    @Delete
    void delete(TripEntity trip);

}
