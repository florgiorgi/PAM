package ar.edu.itba.pam.travelapp.model.trip;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TripDao {

    @Query("SELECT * FROM TripEntity")
    List<TripEntity> getAll();

    @Query("SELECT * FROM TripEntity as t WHERE t.location LIKE :location")
    List<TripEntity> findByLocation(String location);

    @Query("SELECT * FROM TripEntity as t WHERE t.id = :id")
    Trip findById(long id);

    @Insert
    void insert(TripEntity trip);

    @Update
    void update(TripEntity trip);

    @Delete
    void delete(TripEntity trip);

}
