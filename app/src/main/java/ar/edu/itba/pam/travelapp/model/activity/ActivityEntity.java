package ar.edu.itba.pam.travelapp.model.activity;


import java.time.LocalDate;
import java.time.LocalDateTime;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import ar.edu.itba.pam.travelapp.model.converters.LocalDateConverter;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;

@Entity(tableName = "activities")
public class ActivityEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "trip_id")
    private long tripId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @TypeConverters({LocalDateConverter.class})
    @ColumnInfo(name = "date")
    private LocalDate date;

    public ActivityEntity(String name, long tripId, LocalDate date) {
        this.name = name;
        this.tripId = tripId;
        this.date = date;
    }

    @Ignore
    public ActivityEntity(long id, String name, long tripId, LocalDate date) {
        this.id = id;
        this.name = name;
        this.tripId = tripId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
