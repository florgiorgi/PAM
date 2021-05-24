package ar.edu.itba.pam.travelapp.main.history;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.trips.OnTripClickedListener;

public class YearViewHolder extends RecyclerView.ViewHolder {

    public YearViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final String text) {
        final TextView textView = itemView.findViewById(R.id.year);
        textView.setText(text);
    }

}
