package ar.edu.itba.pam.travelapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ViewFlipper;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.pam.travelapp.FtuActivity;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.config.ConfigView;
import ar.edu.itba.pam.travelapp.main.history.HistoryListAdapter;
import ar.edu.itba.pam.travelapp.main.history.HistoryView;
import ar.edu.itba.pam.travelapp.main.trips.CreateTripActivity;
import ar.edu.itba.pam.travelapp.main.trips.ui.TripsView;

public class MainActivity extends AppCompatActivity {

    private static final int TRIPS = 0;
    private static final int HISTORY = 1;
    private static final int CONFIG = 2;
    private static final String FTU = "ftu";
    private static final String SP_ID = "travel-buddy-sp";
    private static final String NIGHT_MODE = "night-mode";

    private RecyclerView tripsRecyclerView;
    private RecyclerView historyRecyclerView;
    private TripListAdapter adapter;
    private HistoryListAdapter historyAdapter;

    private ViewFlipper flipper;

    private TripsView tripsView;
    private HistoryView historyView;
    private ConfigView configView;

    private FloatingActionButton floatingButtonCreate;
    private SwitchMaterial nightModeSwitch;
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

        setUpList();
        setUpHistory();
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
        setUpNightModeSwitch();
    }

    private void setUpNightModeSwitch() {
        //        sp.edit().putBoolean(NIGHT_MODE, false).apply();
        int currentNightModeSetting = AppCompatDelegate.getDefaultNightMode();
        System.out.println("yes: " + AppCompatDelegate.MODE_NIGHT_YES);
        System.out.println("no: " + AppCompatDelegate.MODE_NIGHT_NO);
        System.out.println("no: " + AppCompatDelegate.MODE_NIGHT_UNSPECIFIED);
        nightModeSwitch = findViewById(R.id.switch_night_mode);
        nightModeSwitch.setChecked(true);
        if (currentNightModeSetting == AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
                || currentNightModeSetting == AppCompatDelegate.MODE_NIGHT_NO) {
            System.out.println("set false: ");
            nightModeSwitch.setChecked(false);
        }
        nightModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                buttonView.setChecked(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                buttonView.setChecked(false);
            }
        });
    }

    private void setUpHistoryView() {
        historyView = findViewById(R.id.history);
    }

    private void setUpTripsView() {
        tripsView = findViewById(R.id.trip_list);
    }

    private void setUpList() {
        tripsRecyclerView = findViewById(R.id.trip_list);
        tripsRecyclerView.setHasFixedSize(true);
        adapter = new TripListAdapter(createDataSetUpcoming(), this);
        tripsRecyclerView.setAdapter(adapter);
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setUpHistory() {
        historyRecyclerView = findViewById(R.id.history);
        historyRecyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryListAdapter(createDataSetHistory(), this);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    //Aca hay que traer la data de los upcoming trips de la bd
    private List<String> createDataSetUpcoming() {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("BUENOS AIRES");
        }
        return list;
    }

    //Aca hay que traer la data de los trips viejos de la bd
    private List<String> createDataSetHistory() {
        final List<String> list = new ArrayList<>();
        list.add("2020");
        for (int i = 0; i < 5; i++) {
            list.add("BUENOS AIRES");
        }
        list.add("2019");
        for (int i = 0; i < 5; i++) {
            list.add("BUENOS AIRES");
        }
        return list;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}