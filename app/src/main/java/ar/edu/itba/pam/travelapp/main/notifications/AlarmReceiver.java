package ar.edu.itba.pam.travelapp.main.notifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.di.main.TripContainer;
import ar.edu.itba.pam.travelapp.di.main.TripContainerLocator;
import ar.edu.itba.pam.travelapp.main.MainActivity;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;
import io.reactivex.disposables.Disposable;

public class AlarmReceiver extends BroadcastReceiver {

    private Disposable disposable;
    private List<Trip> upcomingTrips;
    private Context context;
    private Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        this.intent = intent;
        getUpcomingTrips();

    }

    private void getUpcomingTrips() {
        TripContainer container = TripContainerLocator.locateComponent(context);
        TripRepository tripRepository = container.getTripRepository();
        SchedulerProvider schedulerProvider = container.getSchedulerProvider();

        this.disposable = tripRepository.getTrips()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onUpcomingTripsReceived, error -> {
                    //do nothing
                });
    }

    private void onUpcomingTripsReceived(final List<Trip> trips) {
        LocalDate today = LocalDate.now();
        List<Trip> upcoming = trips.stream().filter(t -> (t.getTo().isAfter(today) || t.getTo().isEqual(today))).collect(Collectors.toList());
        this.upcomingTrips = upcoming;
        boolean tripsIn2Days = checkTripsIn2Days(upcomingTrips);
        boolean tripsIn7Days = checkTripsIn7Days(upcomingTrips);
        System.out.println("In 7 days " + tripsIn7Days);
        System.out.println("In 2 days " + tripsIn2Days);

        if(tripsIn2Days) {
            sendNotification(2);
        }
        if(tripsIn7Days) {
            sendNotification(7);
        }
    }

    private boolean checkTripsIn2Days(List<Trip> trips) {
        LocalDate today = LocalDate.now();
        for (Trip t : trips) {
            System.out.println(Duration.between(today.atStartOfDay(),t.getFrom().atStartOfDay()).toDays());
            if(Duration.between(today.atStartOfDay(),t.getFrom().atStartOfDay()).toDays() == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTripsIn7Days(List<Trip> trips) {
        LocalDate today = LocalDate.now();
        for (Trip t : trips) {
            System.out.println(Duration.between(today.atStartOfDay(),t.getFrom().atStartOfDay()).toDays());
            if(Duration.between(today.atStartOfDay(),t.getFrom().atStartOfDay()).toDays() == 7) {
                return true;
            }
        }
        return false;
    }

    private void sendNotification(int days) {
        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"TravelBuddy")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Upcoming trip")
                .setContentText("Your trip is in " + days + " days! Don't forget to plan your activities")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123*days,builder.build());
    }

}
