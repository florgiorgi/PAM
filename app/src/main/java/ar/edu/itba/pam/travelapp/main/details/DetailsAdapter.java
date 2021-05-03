package ar.edu.itba.pam.travelapp.main.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.TripViewHolder;
import ar.edu.itba.pam.travelapp.main.history.YearViewHolder;

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> dataset;

    public DetailsAdapter(List<String> dataset) {
        this.dataset = dataset;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                final View view_details = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_trip_details, parent, false);
                return new DetailsViewHolder(view_details);
            default:
                final View view_day = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_day, parent, false);
                return new DayViewHolder(view_day);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case 0:
                ((DetailsViewHolder)holder).bind(dataset.get(position));
                break;
            default:
                ((DayViewHolder)holder).bind(dataset.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }
}