package ar.edu.itba.pam.travelapp.main.trips.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.TripListAdapter;


public class TripsViewImpl extends RecyclerView implements TripsView {
    public TripsViewImpl(@NonNull Context context) {
        this(context,null);
    }

    public TripsViewImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TripsViewImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void bind(TripListAdapter adapter) {
        //findViewById(R.id.trip_list);
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setAdapter(adapter);
    }

//        listView = findViewById(R.id.trip_list);    // todo ???
//        listView.setHasFixedSize(true);
//        listView.setAdapter(adapter);
//        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
}
