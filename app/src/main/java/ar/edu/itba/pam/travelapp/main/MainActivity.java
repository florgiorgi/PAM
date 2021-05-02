package ar.edu.itba.pam.travelapp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.itba.pam.travelapp.FtuActivity;
import ar.edu.itba.pam.travelapp.R;

public class MainActivity extends AppCompatActivity {


    private static final String FTU = "ftu";
    private static final String SP_ID = "travel-buddy-sp";

    private RecyclerView view;
    private TripListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sp = getSharedPreferences(SP_ID, MODE_PRIVATE);
        if (sp.getBoolean(FTU, true)) {
            sp.edit().putBoolean(FTU, false).apply();
            startActivity(new Intent(this, FtuActivity.class));
        }

        setUpList();
    }

    private void setUpList() {
        view = findViewById(R.id.trip_list);
        view.setHasFixedSize(true);
        adapter = new TripListAdapter(createDataSet());
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    //Aca hay que traer la data de los trips de la bd
    private List<String> createDataSet() {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("BUENOS AIRES");
        }
        return list;
    }

}