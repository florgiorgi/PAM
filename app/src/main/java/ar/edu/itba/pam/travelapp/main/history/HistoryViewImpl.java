package ar.edu.itba.pam.travelapp.main.history;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.main.TripListAdapter;

public class HistoryViewImpl extends RecyclerView implements HistoryView {
    public HistoryViewImpl(@NonNull Context context) {
        this(context,null);
    }

    public HistoryViewImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HistoryViewImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bind(HistoryListAdapter adapter) {
        //findViewById(R.id.trip_list);
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setAdapter(adapter);
    }
}
