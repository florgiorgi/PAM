package ar.edu.itba.pam.travelapp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.details.DetailsActivity;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

import static java.time.temporal.ChronoUnit.DAYS;

public class TripViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    public View view;

    public TripViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        view = itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailsActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(final Trip trip) {
        final TextView title = itemView.findViewById(R.id.title);
        final TextView date = itemView.findViewById(R.id.date);
        final TextView days_left = itemView.findViewById(R.id.expand);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd");
        String days_left_string = view.getResources().getString(R.string.days_left);
        String current_trip = view.getResources().getString(R.string.current_trip);

        String date_from = trip.getFrom().format(dateFormatter);
        String date_to = trip.getTo().format(dateFormatter);

        title.setText(trip.getLocation());
        date.setText(date_from + " - " + date_to);

        LocalDate today = LocalDate.now();

        long dayDifferenceBeginning = DAYS.between(today, trip.getFrom());
        long dayDifferenceEnd = DAYS.between(today, trip.getTo());

        if (dayDifferenceBeginning >= 0) {
            days_left.setText(dayDifferenceBeginning + " " + days_left_string);
        } else if (dayDifferenceEnd >= 0) {
            days_left.setText(current_trip);
        }
    }

    private long calculateDaysDifference(Calendar thatDay) {
        Calendar today = Calendar.getInstance();
        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);

        return days + 1;
    }
}
