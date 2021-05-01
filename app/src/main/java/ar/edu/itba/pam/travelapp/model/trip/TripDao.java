package ar.edu.itba.pam.travelapp.model.trip;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip")
    List<Trip> getAll();

    @Query("SELECT * FROM trip WHERE trip.location LIKE :location")
    List<Trip> findByLocation(String location);

    @Query("SELECT * FROM trip WHERE trip.id = :id")
    Trip findById(long id);

    @Insert
    void insert(Trip trip);

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

}
