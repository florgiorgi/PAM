package ar.edu.itba.pam.travelapp.tripdetail;

import android.os.Bundle;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.activity.ActivityMapper;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRoomRepository;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

public class DetailsActivity extends AppCompatActivity implements DetailsView, OnNewActivityClickedListener {

    private RecyclerView detailsRecyclerView;
    private DetailsAdapter detailsAdapter;
    private Trip trip;

    private DetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        this.trip = (Trip) getIntent().getSerializableExtra("trip");
        createPresenter();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewAttached();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onViewDetached();
    }

    private void initView() {
        detailsRecyclerView = findViewById(R.id.trip_details);
        detailsRecyclerView.setHasFixedSize(true);
        detailsAdapter = new DetailsAdapter(trip, this);
        detailsAdapter.setOnClickListener(this);
        detailsRecyclerView.setAdapter(detailsAdapter);
        detailsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void createPresenter() {
        presenter = (DetailsPresenter) getLastNonConfigurationInstance();
        if (presenter == null) {
            final ActivityMapper mapper = new ActivityMapper();
            final ActivityRepository activityRepository = new ActivityRoomRepository(AppDatabase.getInstance(getApplicationContext()).activityDao(), mapper);
            presenter = new DetailsPresenter(activityRepository, trip, this);
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void showActivitiesErrorMessage() {
        Toast.makeText(DetailsActivity.this, "Error getting trip activties", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNewActivitySuccessMessage() {
        Toast.makeText(DetailsActivity.this, "Activity created successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void bindDataset(Set<LocalDate> dates, Map<LocalDate,List<Activity>> activities)  {
        detailsAdapter.update(dates, activities);
    }

    @Override
    public void onClick(String name, LocalDate date) {
        presenter.onActivityCreate(name, date);
    }
}
