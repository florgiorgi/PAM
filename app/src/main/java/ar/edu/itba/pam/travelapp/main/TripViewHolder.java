package ar.edu.itba.pam.travelapp.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
                //Toast.makeText(context,"hola",Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context, DetailsActivity.class));
            }
        });
    }

    public void bind(final Trip trip) {
        final TextView textView = itemView.findViewById(R.id.title);
        // TODO: show trip atributes in view
        textView.setText(trip.getLocation());
    }
}
