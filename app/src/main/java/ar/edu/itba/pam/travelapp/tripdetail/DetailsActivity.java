package ar.edu.itba.pam.travelapp.tripdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.di.tripdetail.DetailsContainer;
import ar.edu.itba.pam.travelapp.di.tripdetail.DetailsContainerLocator;
import ar.edu.itba.pam.travelapp.model.dtos.DayDto;
import ar.edu.itba.pam.travelapp.edit.EditTripActivity;
import ar.edu.itba.pam.travelapp.main.MainActivity;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;


public class DetailsActivity extends AppCompatActivity implements DetailsView, ActivityEventListener, ConfirmDialog.ConfirmDialogListener {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trip_detail_menu, menu);
        return true;
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_trip:
                presenter.onEditTrip();
                return true;
            case R.id.menu_delete_trip:
                presenter.onDeleteTrip();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            DetailsContainer container = DetailsContainerLocator.locateComponent(this);
            presenter = new DetailsPresenter(this, trip, container.getActivityRepository(),
                    container.getTripRepository(), (AndroidSchedulerProvider) container.getSchedulerProvider(),
                    container.getWeatherRepository());
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
    public void showDeletedTripSuccessMessage() {
        Toast.makeText(DetailsActivity.this, "Trip deleted successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(DetailsActivity.this, MainActivity.class));
    }

    @Override
    public void openConfirmDeleteDialog() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.show(getSupportFragmentManager(), "confirm-dialog");
    }

    @Override
    public void bindDaysDataset(Set<LocalDate> dates, Map<LocalDate, DayDto> datesData)  {
        detailsAdapter.update(dates, datesData);
    }

    @Override
    public void onForecastError(Throwable error) {
        String errorMessage = error.getMessage();
        if (errorMessage == null) {
            Toast.makeText(DetailsActivity.this, "Weather data not available: no forecast found for trip", Toast.LENGTH_LONG).show();
        } else if (errorMessage.contains("503")) {
            Toast.makeText(DetailsActivity.this, "Weather data not available: your app has low priority and its request was discarded due to high traffic", Toast.LENGTH_LONG).show();
        } else if (errorMessage.contains("401")) {
            Toast.makeText(DetailsActivity.this, "Weather data not available: your free trial requests have expired for today", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(DetailsActivity.this, "Weather data not available: no forecast found for trip", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCityError(Throwable error) {
        String errorMessage = error.getMessage();
        if (errorMessage == null) {
            Toast.makeText(DetailsActivity.this, "Weather data not available: no city found with that name", Toast.LENGTH_LONG).show();
        } else if (errorMessage.contains("503")) {
            Toast.makeText(DetailsActivity.this, "Weather data not available: your app has low priority and its request was discarded due to high traffic", Toast.LENGTH_LONG).show();
        } else if (errorMessage.contains("401")) {
            Toast.makeText(DetailsActivity.this, "Weather data not available: your free trial requests have expired for today", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(DetailsActivity.this, "Weather data not available: no city found with that name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMoveActivity(LocalDate from, long activityId, LocalDate to) {
        presenter.moveActivity(from, activityId, to);
    }

    @Override
    public void showActivityNotFoundErrorMessage(long activityId) {
        Toast.makeText(DetailsActivity.this, "No activity found with id: " + activityId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startEditTripActivity() {
        Intent intent = new Intent(this, EditTripActivity.class);
        intent.putExtra("trip", trip);
        startActivity(intent);
    }

    @Override
    public void onClickNewActivity(String name, LocalDate date) {
        presenter.onActivityCreate(name, date);
    }

    @Override
    public void onDeleteActivity(Activity activity) {
        presenter.onActivityDelete(activity);
    }

    @Override
    public void onEditActivity(Activity activity, String name) {
        presenter.onActivityEdit(activity, name);
    }

    @Override
    public void confirmDelete() {
        presenter.onConfirmDeleteTrip();
    }
}
