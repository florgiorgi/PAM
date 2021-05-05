package ar.edu.itba.pam.travelapp.main.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.activity.Activity;


public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Activity> dataset;
    private Context context;

    public DetailsAdapter(List<Activity> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
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

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 0) {
            ((DetailsViewHolder) holder).bind(dataset.get(position).getName());
        } else {
            ((DayViewHolder) holder).bind(dataset.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }
}
