package ar.edu.itba.pam.travelapp.main.details;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.RequiresApi;
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
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import io.reactivex.disposables.Disposable;

import static java.time.temporal.ChronoUnit.DAYS;

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView detailsRecyclerView;
    private DetailsAdapter detailsAdapter;
    private Trip trip;

    private AppDatabase database;
    private ActivityRepository activityRepository;
    private ActivityMapper activityMapper;
    private AndroidSchedulerProvider schedulerProvider;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        this.trip = (Trip) getIntent().getSerializableExtra("trip");
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
        this.disposable = activityRepository.findByTripId(trip.getId())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onActivitiesReceived, this::onActivitiesError);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    public void createActivity(String name, LocalDate date) {
        AsyncTask.execute(() -> {
            Activity activity = new Activity(name, this.trip.getId(), date);
            this.activityRepository.insert(activity);
        });
        Toast.makeText(this, "Activity created successfully", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpDetails(List<Activity> activities) {
        Set<LocalDate> datesSet = new LinkedHashSet<>();
        Map<LocalDate, List<Activity>> map = parseActivities(activities, datesSet);

        detailsAdapter = new DetailsAdapter(datesSet, map, this, trip);
        detailsRecyclerView.setAdapter(detailsAdapter);
        detailsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Map<LocalDate, List<Activity>> parseActivities(List<Activity> activities, Set<LocalDate> datesSet) {
        Map<LocalDate, List<Activity>> activitiesMap = new HashMap<>();

        LocalDate from = trip.getFrom();
        LocalDate to = trip.getTo();
        long duration = ChronoUnit.DAYS.between(from, to);

        for (int i = 0; i < duration + 2; i++) {
            activitiesMap.put(from.plusDays(i - 1), new ArrayList<Activity>());
            datesSet.add(from.plusDays(i - 1));
        }

        for (Activity a : activities) {
            activitiesMap.get(a.getDate()).add(a);
        }

        return activitiesMap;
    }

}
