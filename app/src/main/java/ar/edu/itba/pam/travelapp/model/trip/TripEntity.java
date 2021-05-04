package ar.edu.itba.pam.travelapp.model.trip;

import java.util.Calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import ar.edu.itba.pam.travelapp.model.converters.LocalDateConverter;
import ar.edu.itba.pam.travelapp.model.converters.LocalDateTimeConverter;

@Entity
public class TripEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "startDate")
    @TypeConverters({LocalDateConverter.class})
    private Calendar from;

    @ColumnInfo(name = "endDate")
    @TypeConverters({LocalDateConverter.class})
    private Calendar to;

    @ColumnInfo(name = "departure_time")
    @TypeConverters({LocalDateTimeConverter.class})
    private Calendar departureTime;

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

    public TripEntity(String location, Calendar from, Calendar to, TravelMethod travelMethod, String flightNumber, Calendar departureTime){
        this.location = location;
        this.from = from;
        this.to = to;
        this.travelMethod = travelMethod;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Calendar getFrom() {
        return from;
    }

    public void setFrom(Calendar from) {
        this.from = from;
    }

    public Calendar getTo() {
        return to;
    }

    public void setTo(Calendar to) {
        this.to = to;
    }

    public Calendar getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Calendar departureTime) {
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

}
