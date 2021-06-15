package ar.edu.itba.pam.travelapp.tripdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.di.tripdetail.DetailsContainerLocator;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;

public class DetailsActivity extends AppCompatActivity implements DetailsView, OnNewActivityClickedListener {

    private Trip trip;
    private DetailsPresenter presenter;
    private DetailsAdapter detailsAdapter;

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
        final TextView tripName = findViewById(R.id.trip_name);
        final TextView tripDate = findViewById(R.id.trip_date);
        final TextView tripFlightNumber = findViewById(R.id.trip_flight_number);
        final TextView tripDepartureDate = findViewById(R.id.trip_departure_date);
        DateTimeFormatter dateFormatter =  DateTimeFormatter.ofPattern("MMM dd");
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        String departureTime = "";
        if (trip.getDepartureTime() != null) {
            departureTime = trip.getDepartureTime().format(dateTimeFormatter);
        }
        String fromDate = trip.getFrom().format(dateFormatter);
        String toDate = trip.getTo().format(dateFormatter);
        String flightTitle = getResources().getString(R.string.flight_title);
        tripName.setText(trip.getLocation());
        String parsedDate = fromDate + " - " + toDate;
        tripDate.setText(parsedDate);
        String flightString = flightTitle + trip.getFlightNumber();
        tripFlightNumber.setText(flightString);

        if (trip.getFlightNumber().equals("")) {
            tripFlightNumber.setVisibility(View.GONE);
        }
        tripDepartureDate.setText(departureTime);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView detailsRecyclerView = findViewById(R.id.trip_details);
        detailsRecyclerView.setHasFixedSize(true);
        detailsAdapter = new DetailsAdapter();
        detailsAdapter.setOnClickListener(this);
        detailsRecyclerView.setAdapter(detailsAdapter);
        detailsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void createPresenter() {
        Object possibleDetailsPresenter = getLastNonConfigurationInstance();
        if (possibleDetailsPresenter instanceof DetailsPresenter) {
            presenter = (DetailsPresenter) getLastNonConfigurationInstance();
        }
        if (presenter == null) {
            presenter = new DetailsPresenter(this, trip,
                    DetailsContainerLocator.locateComponent(this));
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void showActivitiesErrorMessage() {
        Toast.makeText(DetailsActivity.this, "Error getting trip activities", Toast.LENGTH_LONG).show();
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
    public void bindForecastToDay(ForecastResponse response) {
        // todo: bind forecast to day
        System.out.println("Max: " + response.getDailyForecasts().get(0).getTemperature().getMaximum().getValue());
        System.out.println("Min: " + response.getDailyForecasts().get(0).getTemperature().getMinimum().getValue());
        System.out.println("Day icon (sunny/nublado/etc): " + response.getDailyForecasts().get(0).getDay().getIcon());
//        view.bind(model);
    }

    @Override
    public void onForecastError() {
        // todo: explain the error to the user
    }

    @Override
    public void onClick(String name, LocalDate date) {
        presenter.onActivityCreate(name, date);
    }
}
