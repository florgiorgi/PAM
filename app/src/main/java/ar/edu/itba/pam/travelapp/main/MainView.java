package ar.edu.itba.pam.travelapp.main;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.trip.Trip;

public interface MainView {

    void launchFtu();

    void showTripsScreen();

    void showConfigurationScreen();

    void showHistoryScreen();

    void launchTripDetail(Trip trip);

    void launchCreateTrip();

    void bindUpcomingTrips(List<Trip> trips);

    void bindHistoryTrips(List<Object> model);

    void onTripsError();

    void createNotificationChannel();

    void setUpNotifications();
}
