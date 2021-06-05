package ar.edu.itba.pam.travelapp.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.RequiresApi;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.landing.FtuActivity;
import ar.edu.itba.pam.travelapp.landing.storage.FtuStorage;
import ar.edu.itba.pam.travelapp.landing.storage.SharedPreferencesFTUStorage;
import ar.edu.itba.pam.travelapp.main.config.ConfigView;
import ar.edu.itba.pam.travelapp.main.trips.TripListAdapter;
import ar.edu.itba.pam.travelapp.tripdetail.DetailsActivity;
import ar.edu.itba.pam.travelapp.main.history.HistoryListAdapter;
import ar.edu.itba.pam.travelapp.main.history.HistoryView;
import ar.edu.itba.pam.travelapp.newtrip.CreateTripActivity;
import ar.edu.itba.pam.travelapp.main.trips.OnTripClickedListener;
import ar.edu.itba.pam.travelapp.main.trips.TripsView;
import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.trip.TripRoomRepository;


public class MainActivity extends AppCompatActivity implements MainView, OnTripClickedListener {

    private static final int TRIPS = 0;
    private static final int HISTORY = 1;
    private static final int CONFIG = 2;

    private static final String SP_ID = "travel-buddy-sp";
    public static final String NIGHT_MODE = "night-mode";

    private RecyclerView tripsRecyclerView;
    private RecyclerView historyRecyclerView;
    private TripListAdapter adapter;
    private HistoryListAdapter historyAdapter;

    private ViewFlipper flipper;

    private TripsView tripsView;
    private HistoryView historyView;
    private ConfigView configView;

    private FloatingActionButton floatingButtonCreate;
    private BottomNavigationView navView;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_trips);
        createPresenter();
        initView();
        setUpBottomNavigation();
        if (savedInstanceState != null) {
            boolean startAtConfig = savedInstanceState.getBoolean(NIGHT_MODE);
            if (startAtConfig) {
                setContentView(R.layout.activity_config);
            }
            startAtConfig();
        }
    }

    private void createPresenter() {
        Object possibleMainPresenter = getLastNonConfigurationInstance();
        if (possibleMainPresenter instanceof MainPresenter) {
            presenter = (MainPresenter) possibleMainPresenter;
        }
        if (presenter == null) {
            final SharedPreferences sp = getSharedPreferences(SP_ID, MODE_PRIVATE);
            final FtuStorage storage = new SharedPreferencesFTUStorage(sp);
            final TripMapper mapper = new TripMapper();
            final TripRepository tripRepository = new TripRoomRepository(AppDatabase.getInstance(getApplicationContext()).tripDao(), mapper);
            presenter = new MainPresenter(storage, tripRepository, this);
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    @SuppressLint("NonConstantResourceId")
    private void setUpBottomNavigation() {
        navView = findViewById(R.id.bottom_navigation);
        navView.setSelectedItemId(R.id.trips_tab);

        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.trips_tab:
                    presenter.onTripsScreenSelected();
                    return true;
                case R.id.history_tab:
                    presenter.onHistoryScreenSelected();
                    return true;
                case R.id.config_tab:
                    presenter.onConfigurationScreenSelected();
                    return true;
                default:
                    return false;
            }
        });
    }

    private void startAtConfig() {
        setContentView(R.layout.activity_config);
        navView.setSelectedItemId(R.id.config_tab);
    }

    private void initView() {
        flipper = findViewById(R.id.flipper);
        tripsView = findViewById(R.id.trip_list);
        historyView = findViewById(R.id.history);
        configView = findViewById(R.id.config);
        floatingButtonCreate = findViewById(R.id.floating_action_button_trip);
        floatingButtonCreate.setOnClickListener(view -> presenter.onCreateTripClicked());
        setUpConfigView();
        setUpHistoryView();
        setUpTripView();
    }

    private void setUpTripView() {
        tripsRecyclerView = findViewById(R.id.trip_list);
        tripsRecyclerView.setHasFixedSize(true);
        adapter = new TripListAdapter();
        adapter.setOnClickListener(this);
        tripsRecyclerView.setAdapter(adapter);
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setUpHistoryView() {
        historyRecyclerView = findViewById(R.id.history);
        historyRecyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryListAdapter();
        historyAdapter.setOnClickListener(this);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setUpConfigView() {
        configView.bind();
    }

    @Override
    public void launchFtu() {
        startActivity(new Intent(this, FtuActivity.class));
    }

    @Override
    public void showTripsScreen() {
        flipper.setDisplayedChild(TRIPS);
        floatingButtonCreate.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConfigurationScreen() {
        flipper.setDisplayedChild(CONFIG);
        floatingButtonCreate.setVisibility(View.GONE);
    }

    @Override
    public void showHistoryScreen() {
        flipper.setDisplayedChild(HISTORY);
        floatingButtonCreate.setVisibility(View.GONE);
    }

    @Override
    public void launchTripDetail(Trip trip) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("trip", trip);
        startActivity(intent);
    }

    @Override
    public void launchCreateTrip() {
        startActivity(new Intent(MainActivity.this, CreateTripActivity.class));
    }

    @Override
    public void bindUpcomingTrips(final List<Trip> trips) {
        adapter.update(trips);
    }

    @Override
    public void bindHistoryTrips(final List<Object> model) {
        historyAdapter.update(model);
    }

    @Override
    public void onTripsError() {
        Toast.makeText(MainActivity.this, "Error: couldn't fetch trips from database",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(Trip trip) {
        presenter.onTripClicked(trip);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(NIGHT_MODE, configView.wasNightModeToggled());
        super.onSaveInstanceState(outState);
    }
}