package ar.edu.itba.pam.travelapp.model.trip;

import java.time.LocalDate;
import java.time.LocalDateTime;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Trip {

    public static enum TRAVEL_METHOD {
        AIRPLANE, BOAT, CAR, TRAIN, BICYCLE, FOOT, OTHER
    }

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private TRAVEL_METHOD travelMethod;

    @Nullable
    private String description;

    @Nullable
    private LocalDateTime departureTime;

    @Nullable
    private String flightNumber;

    @Nullable
    private String seat;

    @Nullable
    private String googleId;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TRAVEL_METHOD getTravelMethod() {
        return travelMethod;
    }

    public void setTravelMethod(TRAVEL_METHOD travelMethod) {
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
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(@Nullable LocalDateTime departureTime) {
        this.departureTime = departureTime;
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
}
