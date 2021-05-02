package ar.edu.itba.pam.travelapp.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.itba.pam.travelapp.R;

public class TripListAdapter extends RecyclerView.Adapter<TripViewHolder> {

    private List<String> dataset;
    
    public TripListAdapter(List<String> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_trip, parent, false);

        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        holder.bind(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }
}
