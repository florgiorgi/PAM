package ar.edu.itba.pam.travelapp.model.trip;

import java.util.List;

import io.reactivex.Flowable;

public class TripRoomRepository implements TripRepository {

    private final TripDao dao;
    private final TripMapper mapper;

    private Flowable<List<Trip>> trips;

    public TripRoomRepository(final TripDao dao, TripMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Flowable<List<Trip>> getTrips() {
        if (trips == null) {
            trips = dao.getTrips().map(mapper::toModel);
        }
        return trips;
    }

    @Override
    public Flowable<Trip> findById(long tripId) {
        return dao.findById(tripId).map(mapper::toModel);
    }

    @Override
    public Flowable<List<Trip>> findByLocation(String location) {
        return dao.findByLocation(location).map(mapper::toModel);
    }

    @Override
    public void insertTrip(Trip trip) {
        this.trips = null;
        dao.insert(mapper.toEntity(trip));
    }

    @Override
    public void updateTrip(Trip trip) {
        this.trips = null;
        dao.update(mapper.toEntity(trip));
    }

    @Override
    public void deleteTrip(Trip trip) {
        this.trips = null;
        dao.delete(mapper.toEntity(trip));
    }
}
