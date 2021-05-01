package ar.edu.itba.pam.travelapp.model.activity;


import java.util.Date;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import ar.edu.itba.pam.travelapp.model.DateConverter;

@Entity
public class Activity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "trip_id")
    private long tripId;

    private String name;

    @Nullable
    private String description;

    @TypeConverters({DateConverter.class})
    private Date start;

    @Nullable
    @TypeConverters({DateConverter.class})
    private Date end;

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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Nullable
    public Date getEnd() {
        return end;
    }

    public void setEnd(@Nullable Date end) {
        this.end = end;
    }
}
