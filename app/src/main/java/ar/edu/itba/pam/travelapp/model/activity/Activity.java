package ar.edu.itba.pam.travelapp.model.activity;


import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import ar.edu.itba.pam.travelapp.model.CalendarConverter;


@Entity
public class Activity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "trip_id")
    private long tripId;

    private String name;

    @Nullable
    private String description;

    @TypeConverters({CalendarConverter.class})
    private Calendar start;

    @Nullable
    @TypeConverters({CalendarConverter.class})
    private Calendar end;

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

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    @Nullable
    public Calendar getEnd() {
        return end;
    }

    public void setEnd(@Nullable Calendar end) {
        this.end = end;
    }
}
