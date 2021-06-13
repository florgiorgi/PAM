package ar.edu.itba.pam.travelapp.tripdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.trip.Trip;


public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<LocalDate> dataset;
    private final Map<LocalDate, List<Activity>> allData;

    // TODO: remove this!
    private final Context context;

    private OnNewActivityClickedListener listener;

    public DetailsAdapter(Context context) {
        this.context = context;
        this.dataset = new ArrayList<>();
        this.allData = new HashMap<>();
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
        final View viewDay = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_day, parent, false);
        return new DayViewHolder(viewDay, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((DayViewHolder) holder).bind(allData.get(dataset.get(position)), position, dataset.get(position));
        ((DayViewHolder) holder).setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setOnClickListener(OnNewActivityClickedListener listener) {
        this.listener = listener;
    }
}
