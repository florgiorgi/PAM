package ar.edu.itba.pam.travelapp.model.trip;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

public class TripMapper {

    public Trip toModel(TripEntity entity) {
        return new Trip(entity.getLocation(), entity.getFrom(), entity.getTo(), entity.getTravelMethod(), entity.getDepartureTime(), entity.getFlightNumber());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Trip> toModel(List<TripEntity> entities) {
        List<Trip> models = new ArrayList<>();
        entities.forEach(e -> models.add(toModel(e)));
        return models;
    }

    public TripEntity toEntity(Trip trip) {
        return new TripEntity(trip.getLocation(), trip.getFrom(), trip.getTo(), trip.getTravelMethod(), trip.getDepartureTime(), trip.getFlightNumber());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<TripEntity> toEntity(List<Trip> trips) {
        List<TripEntity> entities = new ArrayList<>();
        trips.forEach(t -> entities.add(toEntity(t)));
        return entities;
    }



}
