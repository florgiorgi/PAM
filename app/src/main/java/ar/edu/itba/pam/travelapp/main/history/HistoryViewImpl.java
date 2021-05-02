package ar.edu.itba.pam.travelapp.main.history;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryViewImpl extends RecyclerView implements HistoryView {
    public HistoryViewImpl(@NonNull Context context) {
        super(context);
    }

    public HistoryViewImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HistoryViewImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
