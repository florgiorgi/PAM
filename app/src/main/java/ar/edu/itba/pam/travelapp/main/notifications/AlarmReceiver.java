package ar.edu.itba.pam.travelapp.main.notifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.di.main.TripContainer;
import ar.edu.itba.pam.travelapp.di.main.TripContainerLocator;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.tripdetail.DetailsActivity;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;
import io.reactivex.disposables.Disposable;

public class AlarmReceiver extends BroadcastReceiver {

    private Disposable disposable;
    private List<Trip> upcomingTrips;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
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
        this.upcomingTrips = trips.stream().filter(t -> (t.getTo().isAfter(today) || t.getTo().isEqual(today))).collect(Collectors.toList());
        List<Trip> tripsIn2Days = checkTripsInXDays(upcomingTrips,2);
        List<Trip> tripsIn7Days = checkTripsInXDays(upcomingTrips,7);
        System.out.println("In 7 days " + tripsIn7Days);
        System.out.println("In 2 days " + tripsIn2Days);

        if(tripsIn2Days.size() > 0) {
            for (Trip t : tripsIn2Days) {
                sendNotification(2,t);
            }
        }
        if(tripsIn7Days.size() > 0) {
            for (Trip t : tripsIn7Days) {
                sendNotification(7,t);
            }
        }
    }

    private List<Trip> checkTripsInXDays(List<Trip> trips, int num) {
        List<Trip> tripsInXDays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Trip t : trips) {
            if(Duration.between(today.atStartOfDay(),t.getFrom().atStartOfDay()).toDays() == num) {
                tripsInXDays.add(t);
            }
        }
        return tripsInXDays;
    }

    private void sendNotification(int days, Trip trip) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("trip", trip);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXTRA_LAUNCHED_BY_NOTIFICATION",true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,(int)trip.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"TravelBuddy")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Your trip to " + trip.getLocation() + " is in " + days + " days!")
                .setContentText("Don't forget to plan your activities")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify((int)(days*trip.getId()),builder.build());
    }

}
