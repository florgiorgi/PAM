package ar.edu.itba.pam.travelapp.main.config;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ar.edu.itba.pam.travelapp.R;


public class ConfigViewImpl extends LinearLayout implements ConfigView {

    public ConfigViewImpl(Context context) {
        this(context, null);
    }

    public ConfigViewImpl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConfigViewImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.activity_config, this);
        setOrientation(VERTICAL);

    }

    @Override
    public void bind() {
        // todo
    }

    @Override
    public void handleNightModeSwitch(int mode) {
        // todo
    }

//    private void setUpList() {
//        view = findViewById(R.id.trip_list);
//        view.setHasFixedSize(true);
//        adapter = new TripListAdapter(createDataSet());
//        view.setAdapter(adapter);
//        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//    }
}
