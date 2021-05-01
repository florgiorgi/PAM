package ar.edu.itba.pam.travelapp.model.trip;

import java.time.LocalDateTime;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import ar.edu.itba.pam.travelapp.model.DateConverter;


@Entity
public class Trip {

    public static enum TRAVEL_METHOD {
        AIRPLANE, BOAT, CAR, TRAIN, BICYCLE, FOOT, OTHER
    }

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String location;

    @TypeConverters({DateConverter.class})
    private Date startDate;

    @TypeConverters({DateConverter.class})
    private Date endDate;
    private TRAVEL_METHOD travelMethod;

    @Nullable
    private String description;

    @Nullable
    @TypeConverters({DateConverter.class})
    private Date departureTime;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(@Nullable Date departureTime) {
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
