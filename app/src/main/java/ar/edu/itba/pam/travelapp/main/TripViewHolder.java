package ar.edu.itba.pam.travelapp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(final Trip trip) {
        final TextView title = itemView.findViewById(R.id.title);
        final TextView date = itemView.findViewById(R.id.date);
        final TextView days_left = itemView.findViewById(R.id.expand);

        String days_left_string = view.getResources().getString(R.string.days_left);
        String current_trip = view.getResources().getString(R.string.current_trip);

        String date_from_month = trip.getFrom().getMonth().toString().substring(0,1).concat(trip.getFrom().getMonth().toString().substring(1,3).toLowerCase());
        int date_from_day = trip.getFrom().getDayOfMonth();
        String date_to_month = trip.getTo().getMonth().toString().substring(0,1).concat(trip.getTo().getMonth().toString().substring(1,3).toLowerCase());
        int date_to_day = trip.getTo().getDayOfMonth();

        title.setText(trip.getLocation());
        date.setText(date_from_month + " " + date_from_day + " - " + date_to_month + " " + date_to_day);

        LocalDate today = LocalDate.now();

        long dayDifferenceBeginning = DAYS.between(today, trip.getFrom());
        long dayDifferenceEnd = DAYS.between(today, trip.getTo());

        if (dayDifferenceBeginning > 0) {
            days_left.setText(dayDifferenceBeginning + " " + days_left_string);
        } else if (dayDifferenceEnd >= 0) {
            days_left.setText(current_trip);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //context.startActivity(new Intent(context, DetailsActivity.class));

                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("trip",trip);
                context.startActivity(i);
            }
        });
    }

}
