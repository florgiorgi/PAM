package ar.edu.itba.pam.travelapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.pam.travelapp.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView view;
    private TripListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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