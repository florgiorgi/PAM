package ar.edu.itba.pam.travelapp.model.trip;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Trip implements Serializable {
    private long id;
    private String tripName;
    private String location;
    private LocalDate from;
    private LocalDate to;
    private TravelMethod travelMethod;
    private LocalDateTime departureTime;
    private String description;
    private String flightNumber;
    private String seat;
    private String googleApiId;
    private String locationKey;

    public Trip(String tripName, String location, LocalDate from, LocalDate to, TravelMethod travelMethod, String locationKey) {
        this.tripName = tripName;
        this.location = location;
        this.from = from;
        this.to = to;
        this.travelMethod = travelMethod;
        this.locationKey = locationKey;
    }

    public Trip(String tripName, String location, LocalDate from, LocalDate to, TravelMethod travelMethod, LocalDateTime departureTime, String flightNumber, String locationKey) {
        this.tripName = tripName;
        this.location = location;
        this.from = from;
        this.to = to;
        this.travelMethod = travelMethod;
        this.departureTime = departureTime;
        this.flightNumber = flightNumber;
        this.locationKey = locationKey;
    }

    public Trip(long id, String tripName, String location, LocalDate from, LocalDate to, TravelMethod travelMethod, LocalDateTime departureTime, String flightNumber, String locationKey) {
        this.id = id;
        this.tripName = tripName;
        this.location = location;
        this.from = from;
        this.to = to;
        this.travelMethod = travelMethod;
        this.departureTime = departureTime;
        this.flightNumber = flightNumber;
        this.locationKey = locationKey;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public TravelMethod getTravelMethod() {
        return travelMethod;
    }

    public void setTravelMethod(TravelMethod travelMethod) {
        this.travelMethod = travelMethod;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getGoogleApiId() {
        return googleApiId;
    }

    public void setGoogleApiId(String googleApiId) {
        this.googleApiId = googleApiId;
    }

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public boolean isLocationKeySet() {
        return locationKey != null;
    }
}
