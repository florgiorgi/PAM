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
import android.widget.Button;
import android.widget.ViewFlipper;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ar.edu.itba.pam.travelapp.FtuActivity;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.config.ConfigView;
import ar.edu.itba.pam.travelapp.main.history.HistoryListAdapter;
import ar.edu.itba.pam.travelapp.main.history.HistoryView;
import ar.edu.itba.pam.travelapp.main.trips.CreateTripActivity;
import ar.edu.itba.pam.travelapp.main.trips.ui.TripsView;
import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

public class MainActivity extends AppCompatActivity implements TripsAsyncTask.AsyncResponse {

    private static final int TRIPS = 0;
    private static final int HISTORY = 1;
    private static final int CONFIG = 2;
    private static final String FTU = "ftu";
    private static final String SP_ID = "travel-buddy-sp";

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
    private AppDatabase database;

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

        this.database = AppDatabase.getInstance(MainActivity.this);
        this.getTripsFromDb();

        setUpView();
        setUpBottomNavigation();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        final TripListAdapter adapter = new TripListAdapter(createDataSet());
//        adapter.setOnClickListener();
//        tripsView.bind(adapter);
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

    private void setUpView() {
        flipper = findViewById(R.id.flipper);
        setUpTripsView();
        setUpHistoryView();
        setUpConfigView();
    }

    private void setUpConfigView() {
        configView = findViewById(R.id.config);
        configView.bind();
    }

    private void setUpHistoryView() {
        historyView = findViewById(R.id.history);
    }

    private void setUpTripsView() {
        tripsView = findViewById(R.id.trip_list);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setUpcomingList(List<Trip> upcomingTrips) {
        tripsRecyclerView = findViewById(R.id.trip_list);
        tripsRecyclerView.setHasFixedSize(true);
        adapter = new TripListAdapter(upcomingTrips, this);
        tripsRecyclerView.setAdapter(adapter);
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setHistoryList(List<Trip> historyTrips) {
        historyRecyclerView = findViewById(R.id.history);
        historyRecyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryListAdapter(parsedHistoryTrips(historyTrips), this);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private List<Object> parsedHistoryTrips(List<Trip> dataset) {
        Map<String, List<Trip>> tripsMap = new HashMap<>();
        Map<Trip, String> auxMap = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

        //create map with trips as keys and their year as values
        for (Trip trip : dataset) {
            //System.out.println(dateFormat.format(trip.getFrom().getTime()));
            auxMap.put(trip, dateFormat.format(trip.getFrom().getTime()));
        }

        //create set with years based on the map's values
        Collection<String> years = auxMap.values();
        SortedSet<String> yearsSet = new TreeSet<>();

        for (String year : years) {
            yearsSet.add(year);
        }

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

    private void getTripsFromDb() {
        //execute the async task -> processFinish will be called after finished
        new TripsAsyncTask(this, database).execute();
    }

    // This gets called once the async task is finished
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void processFinish(List<Trip> trips) {
        Calendar today = Calendar.getInstance();
        List<Trip> upcomingTrips = trips.stream().filter(t -> !t.getTo().before(today)).collect(Collectors.toList());
        List<Trip> historyTrips = trips.stream().filter(t -> t.getTo().before(today)).collect(Collectors.toList());
        setUpcomingList(upcomingTrips);
        setHistoryList(historyTrips);
    }
}