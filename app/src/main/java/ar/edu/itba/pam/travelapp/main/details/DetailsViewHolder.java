package ar.edu.itba.pam.travelapp.main.details;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;

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
        final TextView tripName = itemView.findViewById(R.id.trip_name);
        final TextView tripDate = itemView.findViewById(R.id.trip_date);
        final TextView tripFlightNumber = itemView.findViewById(R.id.trip_flight_number);
        final TextView tripDepartureDate = itemView.findViewById(R.id.trip_departure_date);

        DateTimeFormatter dateFormatter =  DateTimeFormatter.ofPattern("MMM dd");
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("MMM dd HH:mm");
        String departureTime = "";

        if (trip.getDepartureTime() != null) {
           departureTime = trip.getDepartureTime().format(dateTimeFormatter);
        }
       
        String fromDate = trip.getFrom().format(dateFormatter);
        String toDate = trip.getTo().format(dateFormatter);

        String flightTitle = view.getResources().getString(R.string.flight_title);
        tripName.setText(trip.getLocation());
        String parsedDate = fromDate + " - " + toDate;
        tripDate.setText(parsedDate);
        String flightString = flightTitle + trip.getFlightNumber();
        tripFlightNumber.setText(flightString);

        if (trip.getFlightNumber().equals("")) {
            tripFlightNumber.setVisibility(View.GONE);
        }
        tripDepartureDate.setText(departureTime);
    }
}
