package ar.edu.itba.pam.travelapp.di.newtrip;

import android.content.Context;

import ar.edu.itba.pam.travelapp.model.trip.TripRepository;

public interface NewTripContainer {
    Context getApplicationContext();

    TripRepository getTripRepository();
}
