package ar.edu.itba.pam.travelapp.main.details;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;

public class DetailsViewHolder extends RecyclerView.ViewHolder {

    public DetailsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final String text) {
        final TextView textView = itemView.findViewById(R.id.trip_name);

        textView.setText(text);
    }
}
