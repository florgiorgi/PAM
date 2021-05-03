package ar.edu.itba.pam.travelapp.main.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.itba.pam.travelapp.main.TripViewHolder;
import ar.edu.itba.pam.travelapp.R;


public class HistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> dataset;
    private Context context;

    public HistoryListAdapter(List<String> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 6;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                final View view_year = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_year, parent, false);
                return new YearViewHolder(view_year);
            default:
                final View view_trip = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_trip, parent, false);
                return new TripViewHolder(view_trip, context);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case 0:
                ((YearViewHolder) holder).bind(dataset.get(position));
                break;
            default:
                ((TripViewHolder) holder).bind(dataset.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }
}
