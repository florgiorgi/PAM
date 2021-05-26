package ar.edu.itba.pam.travelapp.tripdetail;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.trip.Trip;


public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocalDate> dataset;
    private Map<LocalDate, List<Activity>> allData;
    private final Trip trip;

    // TODO: remove this!
    private Context context;

    private OnNewActivityClickedListener listener;

    public DetailsAdapter(Trip trip, Context context) {
        this.context = context;
        this.dataset = new ArrayList<>();
        this.allData = new HashMap<>();
        this.trip = trip;
    }

    public void update(Set<LocalDate> dates, Map<LocalDate, List<Activity>> activities) {
        dataset.clear();
        allData.clear();
        if (dates != null && activities != null) {
            this.dataset.addAll(dates);
            this.allData.putAll(activities);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            final View viewDetails = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_trip_details, parent, false);
            return new DetailsViewHolder(viewDetails);
        }
        final View viewDay = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_day, parent, false);
        return new DayViewHolder(viewDay, context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 0) {
            ((DetailsViewHolder) holder).bind(trip);
        } else {
            ((DayViewHolder) holder).bind(allData.get(dataset.get(position)), position, dataset.get(position));
            ((DayViewHolder) holder).setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    public void setOnClickListener(OnNewActivityClickedListener listener) {
        this.listener = listener;
    }
}
