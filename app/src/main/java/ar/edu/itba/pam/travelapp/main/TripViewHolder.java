package ar.edu.itba.pam.travelapp.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.details.DetailsActivity;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

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

    public void bind(final Trip trip) {
        final TextView title = itemView.findViewById(R.id.title);
        final TextView date = itemView.findViewById(R.id.date);
        final TextView days_left = itemView.findViewById(R.id.expand);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
        String days_left_string = view.getResources().getString(R.string.days_left1);

        String date_from = dateFormat.format(trip.getFrom().getTime());
        String date_to = dateFormat.format(trip.getTo().getTime());

        // TODO: show trip atributes in view
        title.setText(trip.getLocation());
        date.setText(date_from + " - " + date_to);
        days_left.setText(calculateDaysDifference(trip.getFrom()) + " " + days_left_string);

    }

    private long calculateDaysDifference(Calendar thatDay) {
        Calendar today = Calendar.getInstance();
        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);

        return days+1;
    }
}
