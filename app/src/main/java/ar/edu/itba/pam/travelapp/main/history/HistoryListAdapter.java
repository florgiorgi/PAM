package ar.edu.itba.pam.travelapp.main.history;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import ar.edu.itba.pam.travelapp.main.TripViewHolder;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.Trip;


public class HistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> dataset;
    private Context context;

    public HistoryListAdapter(Context context) {
        this.dataset = new ArrayList<>();
        this.context = context;
    }

    public void update(List<Object> newDataset) {
        dataset.clear();
        if (newDataset != null) {
            dataset.addAll(newDataset);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return dataset.get(position).getClass().equals(String.class) ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            final View viewYear = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_year, parent, false);
            return new YearViewHolder(viewYear);
        }
        final View viewTrip = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_trip, parent, false);
        return new TripViewHolder(viewTrip, context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 0) {
            ((YearViewHolder) holder).bind((String) dataset.get(position));
        } else {
            ((TripViewHolder) holder).bind((Trip) dataset.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }
}
