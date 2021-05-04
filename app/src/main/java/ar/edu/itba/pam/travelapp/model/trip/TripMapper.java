package ar.edu.itba.pam.travelapp.model.trip;

public class TripMapper {

    public Trip fromEntity(TripEntity entity) {
        return new Trip(entity.getLocation(), entity.getFrom(), entity.getTo(), entity.getTravelMethod(), entity.getDepartureTime(), entity.getFlightNumber());
    }

    public TripEntity toEntity(Trip trip) {
        return new TripEntity(trip.getLocation(), trip.getFrom(), trip.getTo(), trip.getTravelMethod(), trip.getDepartureTime(), trip.getFlightNumber());
    }

}
