package ar.edu.itba.pam.travelapp.main;

import androidx.annotation.RequiresApi;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ar.edu.itba.pam.travelapp.landing.FtuActivity;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.config.ConfigView;
import ar.edu.itba.pam.travelapp.main.history.HistoryListAdapter;
import ar.edu.itba.pam.travelapp.main.history.HistoryView;
import ar.edu.itba.pam.travelapp.main.trips.CreateTripActivity;
import ar.edu.itba.pam.travelapp.main.trips.ui.TripsView;
import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.trip.TripRoomRepository;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private static final int TRIPS = 0;
    private static final int HISTORY = 1;
    private static final int CONFIG = 2;
    private static final String FTU = "ftu";
    private static final String SP_ID = "travel-buddy-sp";

    private AppDatabase database;
    private TripRepository tripRepository;
    private TripMapper tripMapper;
    private AndroidSchedulerProvider schedulerProvider;
    private Disposable disposable;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_trips);

        final SharedPreferences sp = getSharedPreferences(SP_ID, MODE_PRIVATE);
        if (sp.getBoolean(FTU, true)) {
            sp.edit().putBoolean(FTU, false).apply();
            startActivity(new Intent(this, FtuActivity.class));
        }
        this.floatingButtonCreate = findViewById(R.id.floating_action_button_trip);
        floatingButtonCreate.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, CreateTripActivity.class));
        });

        initDatabase();
        initView();
        setUpBottomNavigation();
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

    private void initDatabase() {
        this.database = AppDatabase.getInstance(this);
        this.tripMapper = new TripMapper();
        this.tripRepository = new TripRoomRepository(database.tripDao(), tripMapper);
        this.schedulerProvider = new AndroidSchedulerProvider();
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpBottomNavigation() {
        navView = findViewById(R.id.bottom_navigation);
        navView.setSelectedItemId(R.id.trips_tab);
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.trips_tab:
                    flipper.setDisplayedChild(TRIPS);
                    floatingButtonCreate.setVisibility(View.VISIBLE);
                    return true;
                case R.id.history_tab:
                    flipper.setDisplayedChild(HISTORY);
                    floatingButtonCreate.setVisibility(View.GONE);
                    return true;
                case R.id.config_tab:
                    flipper.setDisplayedChild(CONFIG);
                    floatingButtonCreate.setVisibility(View.GONE);
                    return true;
                default:
                    return false;
            }
        });
    }

    private void initView() {
        flipper = findViewById(R.id.flipper);
        // Upcoming
        tripsView = findViewById(R.id.trip_list);
        // History
        historyView = findViewById(R.id.history);
        // Configuration
        configView = findViewById(R.id.config);
        configView.bind();
    }


    private void setUpcomingList(List<Trip> upcomingTrips) {
        tripsRecyclerView = findViewById(R.id.trip_list);
        tripsRecyclerView.setHasFixedSize(true);
        adapter = new TripListAdapter(upcomingTrips, this);
        tripsRecyclerView.setAdapter(adapter);
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setHistoryList(List<Trip> historyTrips) {
        historyRecyclerView = findViewById(R.id.history);
        historyRecyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryListAdapter(parsedHistoryTrips(historyTrips), this);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void onViewAttached() {
        this.disposable = tripRepository.getTrips()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onTripsReceived, this::onTripsError);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onTripsReceived(final List<Trip> trips) {
        LocalDate today = LocalDate.now();
        List<Trip> upcoming = trips.stream().filter(t -> t.getFrom().isBefore(today) || t.getFrom().isEqual(today)).collect(Collectors.toList());
        List<Trip> history = trips.stream().filter(t -> t.getTo().isBefore(today)).collect(Collectors.toList());
        setUpcomingList(upcoming);
        setHistoryList(history);
    }

    private void onTripsError(final Throwable error) {
        // explain error to the user
        Toast.makeText(MainActivity.this, "Error: couldn't fetch trips from database", Toast.LENGTH_LONG);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Object> parsedHistoryTrips(List<Trip> dataset) {
        Map<String, List<Trip>> tripsMap = new HashMap<>();
        Map<Trip, String> auxMap = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

        //create map with trips as keys and their year as values
        for (Trip trip : dataset) {
            LocalDate date = trip.getFrom();
            String year = String.valueOf(date.getYear());
            auxMap.put(trip, year);
        }

        //create set with years based on the map's values
        SortedSet<String> yearsSet = new TreeSet<>(auxMap.values());

        //create map with year as key and a list of trips from that year as value
        for (String year : yearsSet) {
            List<Trip> trips = new ArrayList<>();
            for (Map.Entry e : auxMap.entrySet()) {
                if (e.getValue().equals(year)) {
                    trips.add((Trip) e.getKey());
                }
            }
            tripsMap.put(year, trips);
        }

        //reverse order the years in the set
        Set<String> auxSet = tripsMap.keySet();
        List list = new ArrayList(auxSet);
        Collections.sort(list, Collections.reverseOrder());
        Set<String> orderedYearsSet = new LinkedHashSet(list);

        //create list with years and trips at the same level to pass to the adapter
        List<Object> parsedData = new ArrayList<>();

        for (String k : orderedYearsSet) {
            parsedData.add(k);
            for (Trip t : tripsMap.get(k)) {
                parsedData.add(t);
            }
        }
        return parsedData;
    }

}