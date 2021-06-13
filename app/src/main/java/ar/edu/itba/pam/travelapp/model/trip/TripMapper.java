package ar.edu.itba.pam.travelapp.model.trip;

import java.util.ArrayList;
import java.util.List;


public class TripMapper {

    public Trip toModel(TripEntity entity) {
        return new Trip(entity.getId(), entity.getLocation(), entity.getFrom(), entity.getTo(), entity.getTravelMethod(), entity.getDepartureTime(), entity.getFlightNumber());
    }

    public List<Trip> toModel(List<TripEntity> entities) {
        List<Trip> models = new ArrayList<>();
        entities.forEach(e -> models.add(toModel(e)));
        return models;
    }

    public TripEntity toEntity(Trip trip) {
        return new TripEntity(trip.getId(), trip.getLocation(), trip.getFrom(), trip.getTo(), trip.getTravelMethod(), trip.getDepartureTime(), trip.getFlightNumber());
    }

    public List<TripEntity> toEntity(List<Trip> trips) {
        List<TripEntity> entities = new ArrayList<>();
        trips.forEach(t -> entities.add(toEntity(t)));
        return entities;
    }

}
