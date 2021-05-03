package ar.edu.itba.pam.travelapp.main.details;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.pam.travelapp.R;

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView detailsRecyclerView;
    private DetailsAdapter detailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trip_details);

        setUpDetails();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setUpDetails() {
        detailsRecyclerView = findViewById(R.id.trip_details);
        detailsRecyclerView.setHasFixedSize(true);
        detailsAdapter = new DetailsAdapter(createDataSetDetails());
        detailsRecyclerView.setAdapter(detailsAdapter);
        detailsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    //Aca hay que traer la data de los days de la bd
    private List<String> createDataSetDetails() {
        final List<String> list = new ArrayList<>();
        list.add("BUENOS AIRES");
        for (int i = 0; i < 20; i++) {
            list.add("Day " + i);
        }
        return list;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
