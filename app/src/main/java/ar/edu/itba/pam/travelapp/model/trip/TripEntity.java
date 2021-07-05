package ar.edu.itba.pam.travelapp.model.trip;

import java.time.LocalDate;
import java.time.LocalDateTime;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import ar.edu.itba.pam.travelapp.model.converters.LocalDateConverter;
import ar.edu.itba.pam.travelapp.model.converters.LocalDateTimeConverter;


@Entity(tableName = "trips")
public class TripEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "trip_name")
    private String tripName;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "startDate")
    @TypeConverters({LocalDateConverter.class})
    private LocalDate from;

    @ColumnInfo(name = "endDate")
    @TypeConverters({LocalDateConverter.class})
    private LocalDate to;

    @ColumnInfo(name = "departure_time")
    @TypeConverters({LocalDateTimeConverter.class})
    private LocalDateTime departureTime;

    @ColumnInfo(name = "travel_method")
    private TravelMethod travelMethod;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "flight_number")
    private String flightNumber;

    @ColumnInfo(name = "seat_number")
    private String seat;

    @ColumnInfo(name = "google_id")
    private String googleId;

    @ColumnInfo(name = "location_key")
    private String locationKey;

    public TripEntity(String tripName, String location, LocalDate from, LocalDate to, TravelMethod travelMethod, LocalDateTime departureTime,  String flightNumber, String locationKey) {
        this.tripName = tripName;
        this.location = location;
        this.from = from;
        this.to = to;
        this.travelMethod = travelMethod;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.locationKey = locationKey;
    }

    @Ignore
    public TripEntity(long id, String tripName, String location, LocalDate from, LocalDate to, TravelMethod travelMethod, LocalDateTime departureTime,  String flightNumber, String locationKey) {
        this.id = id;
        this.tripName = tripName;
        this.location = location;
        this.from = from;
        this.to = to;
        this.travelMethod = travelMethod;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public TravelMethod getTravelMethod() {
        return travelMethod;
    }

    public void setTravelMethod(TravelMethod travelMethod) {
        this.travelMethod = travelMethod;
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

    public String getLocationKey() {
        return locationKey;
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

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }
}
