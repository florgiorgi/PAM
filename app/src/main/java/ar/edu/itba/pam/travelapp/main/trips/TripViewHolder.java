package ar.edu.itba.pam.travelapp.main.trips;

import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

import static java.time.temporal.ChronoUnit.DAYS;

public class TripViewHolder extends RecyclerView.ViewHolder {
    public View view;
    private OnTripClickedListener listener;

    public TripViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setOnClickListener(OnTripClickedListener listener) {
        this.listener = listener;
    }

    public void bind(final Trip trip) {
        System.out.println("trip name: " + trip.getTripName());
        System.out.println("trip location: " + trip.getLocation());
        System.out.println("trip location key: " + trip.getLocationKey());
        final TextView title = itemView.findViewById(R.id.title);
        final TextView location = itemView.findViewById(R.id.location);
        final TextView date = itemView.findViewById(R.id.date);
        final TextView daysLeft = itemView.findViewById(R.id.expand);

        String daysLeftString = view.getResources().getString(R.string.days_left);
        String currentTrip = view.getResources().getString(R.string.current_trip);

        String dateFromMonth = trip.getFrom().getMonth().toString().substring(0,1).concat(trip.getFrom().getMonth().toString().substring(1,3).toLowerCase());
        int dateFromDay = trip.getFrom().getDayOfMonth();
        String dateToMonth = trip.getTo().getMonth().toString().substring(0,1).concat(trip.getTo().getMonth().toString().substring(1,3).toLowerCase());
        int dateToDay = trip.getTo().getDayOfMonth();

        String tripName = trip.getTripName();
        title.setText(tripName != null && !tripName.isEmpty() ? tripName : trip.getLocation());
        location.setText(trip.getLocation());
        date.setText(dateFromMonth + " " + dateFromDay + " - " + dateToMonth + " " + dateToDay);

        LocalDate today = LocalDate.now();

        long dayDifferenceBeginning = DAYS.between(today, trip.getFrom());
        long dayDifferenceEnd = DAYS.between(today, trip.getTo());

        if (dayDifferenceBeginning > 0) {
            daysLeft.setText(dayDifferenceBeginning + " " + daysLeftString);
        } else if (dayDifferenceEnd >= 0) {
            daysLeft.setText(currentTrip);
        }
        view.setOnClickListener(v -> {
            listener.onClick(trip);
        });
    }
}
