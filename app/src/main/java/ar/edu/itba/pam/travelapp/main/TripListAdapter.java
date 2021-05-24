package ar.edu.itba.pam.travelapp.main;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

public class TripListAdapter extends RecyclerView.Adapter<TripViewHolder> {

    private List<Trip> dataset;
    private Context context;
    
    public TripListAdapter(Context context) {
        this.dataset = new ArrayList<>();
        this.context = context;
    }

    public void update(List<Trip> newDataset) {
        dataset.clear();
        if (newDataset != null) {
            dataset.addAll(newDataset);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_trip, parent, false);

        return new TripViewHolder(view, context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        holder.bind(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }
}
