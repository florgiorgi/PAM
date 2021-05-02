package ar.edu.itba.pam.travelapp.main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;


public class TripViewHolder extends RecyclerView.ViewHolder {

    public TripViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final String text) {
        final TextView textView = itemView.findViewById(R.id.title);

        textView.setText(text);
    }
}
