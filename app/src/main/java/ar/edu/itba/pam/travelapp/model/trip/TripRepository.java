package ar.edu.itba.pam.travelapp.model.trip;

import java.util.List;

import io.reactivex.Flowable;

public interface TripRepository {

    Flowable<List<Trip>> getTrips();

    Flowable<Trip> findById(long tripId);

    Flowable<List<Trip>> findByLocation(String location);

    void insertTrip(Trip trip);

    void updateTrip(Trip trip);

    void deleteTrip(Trip trip);

}
