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
        final TextView tripName = itemView.findViewById(R.id.trip_name);
        final TextView tripDate = itemView.findViewById(R.id.trip_date);
        final TextView tripFlightNumber = itemView.findViewById(R.id.trip_flight_number);
        final TextView tripDepartureDate = itemView.findViewById(R.id.trip_departure_date);

        String dateFromMonth = trip.getFrom().getMonth().toString().substring(0, 1).concat(trip.getFrom().getMonth().toString().substring(1, 3).toLowerCase());
        int dateFromDay = trip.getFrom().getDayOfMonth();
        String dateToMonth = trip.getTo().getMonth().toString().substring(0, 1).concat(trip.getTo().getMonth().toString().substring(1, 3).toLowerCase());
        int dateToDay = trip.getTo().getDayOfMonth();

        String departureMonth = trip.getDepartureTime().getMonth().toString().substring(0, 1).concat(trip.getFrom().getMonth().toString().substring(1, 3).toLowerCase());
        int departureDay = trip.getDepartureTime().getDayOfMonth();
        int departureHours = trip.getDepartureTime().getHour();
        int departureMinutes = trip.getDepartureTime().getMinute();

        String flightTitle = view.getResources().getString(R.string.flight_title);

        tripName.setText(trip.getLocation());
        tripDate.setText(dateFromMonth + " " + dateFromDay + " - " + dateToMonth + " " + dateToDay);

        tripFlightNumber.setText(flightTitle + trip.getFlightNumber());
        if (trip.getFlightNumber() == "")
            tripFlightNumber.setVisibility(View.GONE);

        String parsedDepartureMinutes;
        if (departureMinutes < 10) {
            parsedDepartureMinutes = "0" + departureMinutes;
            tripDepartureDate.setText(departureMonth + " " + departureDay + " " + departureHours + ":" + parsedDepartureMinutes);
        } else {
            tripDepartureDate.setText(departureMonth + " " + departureDay + " " + departureHours + ":" + departureMinutes);
        }

    }
}
