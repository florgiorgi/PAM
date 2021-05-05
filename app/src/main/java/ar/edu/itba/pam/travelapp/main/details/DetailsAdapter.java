package ar.edu.itba.pam.travelapp.main.details;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.trip.Trip;


public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocalDate> dataset;
    private Map<LocalDate, List<Activity>> allData;
    private DetailsActivity context;
    private Trip trip;
    private int datasetSize;

    public DetailsAdapter(Set<LocalDate> dataset, Map<LocalDate, List<Activity>> allData, DetailsActivity context, Trip trip) {
        this.dataset = new ArrayList<>(dataset);
        this.allData = allData;
        this.context = context;
        this.trip = trip;
        this.datasetSize = dataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            final View view_details = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_trip_details, parent, false);
            return new DetailsViewHolder(view_details);
        }
        final View view_day = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_day, parent, false);
        return new DayViewHolder(view_day, context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 0) {
            ((DetailsViewHolder) holder).bind(trip);
        } else {
            ((DayViewHolder) holder).bind(allData.get(dataset.get(position)), position, dataset.get(position), trip);
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }
}
