package ar.edu.itba.pam.travelapp.model.trip;

import java.util.List;

import io.reactivex.Flowable;

public class TripRoomRepository implements TripRepository{

    private final TripDao dao;
    private final TripMapper mapper;

    public TripRoomRepository(final TripDao dao, TripMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Flowable<List<Trip>> getTrips() {
        return null;
    }

    @Override
    public Flowable<Trip> findById(long tripId) {
        return null;
    }

    @Override
    public Flowable<List<Trip>> findByLocation(String location) {
        return null;
    }

    @Override
    public void insertTrip(Trip trip) {
        // this.dao.insert();
    }

    @Override
    public void updateTrip(Trip trip) {

    }

    @Override
    public void deleteTrip(Trip trip) {

    }
}
