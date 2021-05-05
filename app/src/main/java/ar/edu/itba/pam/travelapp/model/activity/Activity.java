package ar.edu.itba.pam.travelapp.model.activity;

import java.time.LocalDate;

public class Activity {

    private String name;
    private long tripId;
    private LocalDate date;

    public Activity(String name, long tripId, LocalDate date) {
        this.name = name;
        this.tripId = tripId;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
