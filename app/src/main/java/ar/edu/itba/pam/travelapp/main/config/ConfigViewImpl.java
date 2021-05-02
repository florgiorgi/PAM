package ar.edu.itba.pam.travelapp.main.config;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;

import static android.view.Gravity.CENTER;

public class ConfigViewImpl extends LinearLayout implements ConfigView {
//    private TextView textView;
//    private ImageView imageView;

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

//        textView = findViewById(R.id.text);
//        imageView = findViewById(R.id.image);
    }

    @Override
    public void bind() {
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
