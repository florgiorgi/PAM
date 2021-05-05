package ar.edu.itba.pam.travelapp.main.details;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

public class DetailsViewHolder extends RecyclerView.ViewHolder {

    private View view;

    public DetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(final Trip trip) {
        final TextView trip_name = itemView.findViewById(R.id.trip_name);
        final TextView trip_date = itemView.findViewById(R.id.trip_date);
        final TextView trip_flight_number = itemView.findViewById(R.id.trip_flight_number);
        final TextView trip_departure_date = itemView.findViewById(R.id.trip_departure_date);

        String date_from_month = trip.getFrom().getMonth().toString().substring(0,1).concat(trip.getFrom().getMonth().toString().substring(1,3).toLowerCase());
        int date_from_day = trip.getFrom().getDayOfMonth();
        String date_to_month = trip.getTo().getMonth().toString().substring(0,1).concat(trip.getTo().getMonth().toString().substring(1,3).toLowerCase());
        int date_to_day = trip.getTo().getDayOfMonth();

        String departure_month = trip.getDepartureTime().getMonth().toString().substring(0,1).concat(trip.getFrom().getMonth().toString().substring(1,3).toLowerCase());
        int departure_day = trip.getDepartureTime().getDayOfMonth();
        int departure_hours = trip.getDepartureTime().getHour();
        int departure_minutes = trip.getDepartureTime().getMinute();

        String flight_title = view.getResources().getString(R.string.flight_title);

        trip_name.setText(trip.getLocation());
        trip_date.setText(date_from_month + " " + date_from_day + " - " + date_to_month + " " + date_to_day);
        trip_flight_number.setText(flight_title + trip.getFlightNumber());
        trip_departure_date.setText(departure_month + " " + departure_day + " " + departure_hours + ":" + departure_minutes);

    }
}
