package ar.edu.itba.pam.travelapp.model.trip;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import ar.edu.itba.pam.travelapp.model.CalendarConverter;


@Entity
public class Trip {

    public static enum TravelMethod {
        AIRPLANE, BOAT, CAR, TRAIN, BICYCLE, FOOT, OTHER
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String location;

    @TypeConverters({CalendarConverter.class})
    private Calendar from;

    @TypeConverters({CalendarConverter.class})
    private Calendar to;

    private TravelMethod travelMethod;

    @Nullable
    private String description;

    @Nullable
    @TypeConverters({CalendarConverter.class})
    private Calendar departureTime;

    @Nullable
    private String flightNumber;

    @Nullable
    private String seat;

    @Nullable
    private String googleId;

    public Trip(String name, String location, Calendar from, Calendar to, TravelMethod travelMethod, @Nullable String flightNumber, @Nullable Calendar departureTime){
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Nullable
    public Calendar getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(@Nullable Calendar departureTime) {
        this.departureTime = departureTime;
    }

    public TravelMethod getTravelMethod() {
        return travelMethod;
    }

    public void setTravelMethod(TravelMethod travelMethod) {
        this.travelMethod = travelMethod;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(@Nullable String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Nullable
    public String getSeat() {
        return seat;
    }

    public void setSeat(@Nullable String seat) {
        this.seat = seat;
    }

    @Nullable
    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(@Nullable String googleId) {
        this.googleId = googleId;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", travelMethod=" + travelMethod +
                ", departureTime=" + departureTime +
                ", flightNumber='" + flightNumber + '\'' +
                '}';
    }
}
