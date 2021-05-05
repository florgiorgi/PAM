package ar.edu.itba.pam.travelapp.main.details;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailsViewImpl extends RecyclerView implements DetailsView {
    public DetailsViewImpl(@NonNull Context context) {
        this(context, null);
    }

    public DetailsViewImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailsViewImpl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void bind(DetailsAdapter adapter) {
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setAdapter(adapter);
    }
}
