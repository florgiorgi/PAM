package ar.edu.itba.pam.travelapp.main.details;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.activity.ActivityMapper;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRoomRepository;
import ar.edu.itba.pam.travelapp.model.trip.TripRoomRepository;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import io.reactivex.disposables.Disposable;

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView detailsRecyclerView;
    private DetailsAdapter detailsAdapter;

    private AppDatabase database;
    private ActivityRepository activityRepository;
    private ActivityMapper activityMapper;
    private AndroidSchedulerProvider schedulerProvider;
    private Disposable disposable;

    private long tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        this.tripId = getIntent().getLongExtra("tripId", 1);
        this.detailsRecyclerView = findViewById(R.id.trip_details);
        detailsRecyclerView.setHasFixedSize(true);
        initDatabase();
    }

    @Override
    protected void onStart() {
        onViewAttached();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.dispose();
    }

    private void onViewAttached() {
        this.disposable = activityRepository.findByTripId(tripId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onActivitiesReceived, this::onActivitiesError);
    }

    private void onActivitiesReceived(List<Activity> activities) {
        setUpDetails(activities);
    }

    private void onActivitiesError(Throwable error) {
        Toast.makeText(DetailsActivity.this, "Error getting trip activties", Toast.LENGTH_LONG).show();
    }

    private void initDatabase() {
        this.database = AppDatabase.getInstance(this);
        this.activityMapper = new ActivityMapper();
        this.activityRepository = new ActivityRoomRepository(database.activityDao(), activityMapper);
        this.schedulerProvider = new AndroidSchedulerProvider();
    }

    private void setUpDetails(List<Activity> activities) {
        detailsAdapter = new DetailsAdapter(activities, this);
        detailsRecyclerView.setAdapter(detailsAdapter);
        detailsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

}
