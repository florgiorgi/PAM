package ar.edu.itba.pam.travelapp.main.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.itba.pam.travelapp.main.TripViewHolder;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.Trip;


public class HistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Trip> dataset;
    private Context context;

    public HistoryListAdapter(List<Trip> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 6;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            final View view_year = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_year, parent, false);
            return new YearViewHolder(view_year);
        }
        final View view_trip = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_trip, parent, false);
        return new TripViewHolder(view_trip, context);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 0) {
            // TODO: get year instead of location
            ((YearViewHolder) holder).bind(dataset.get(position).getLocation());
        } else {
            ((TripViewHolder) holder).bind(dataset.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }
}
