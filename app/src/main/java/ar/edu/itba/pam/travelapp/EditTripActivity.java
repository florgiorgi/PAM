package ar.edu.itba.pam.travelapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ar.edu.itba.pam.travelapp.di.newtrip.NewTripContainerLocator;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.tripdetail.DetailsActivity;
import ar.edu.itba.pam.travelapp.utils.DateUtils;

public class EditTripActivity extends AppCompatActivity implements Validator.ValidationListener, EditTripView {

    @NotEmpty
    private EditText from;
    @NotEmpty
    private EditText to;
    @NotEmpty
    @Length(max = 20)
    private EditText destination;
    private EditText flightNumber;
    private EditText departureTime;
    private Spinner transportSpinner;
    private TravelMethod travelMethod;
    private Button cancelButton;
    private Button editButton;

    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDateTime departureDate;

    private Trip trip;

    private StringBuilder dateTimeBuilder;

    private Validator validator;
    private EditTripPresenter presenter;


    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
        this.trip = (Trip) getIntent().getSerializableExtra("trip");
        initValidator();
        initView();
        createPresenter();
    }

    private void initValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void createPresenter() {
        Object possibleEditTripPresenter = getLastNonConfigurationInstance();
        if (possibleEditTripPresenter instanceof EditTripPresenter) {
            this.presenter = (EditTripPresenter) possibleEditTripPresenter;
        }
        if (this.presenter == null) {
            this.presenter = new EditTripPresenter(this,
                    NewTripContainerLocator.locateComponent(this));
        }
    }

    private void initView() {
        editButton = findViewById(R.id.edit_btn);
        editButton.setOnClickListener(view -> validator.validate());

        cancelButton = findViewById(R.id.cancel_edit_btn);
        cancelButton.setOnClickListener(view -> presenter.onCancel());

        from = findViewById(R.id.from_edit);
        from.setOnClickListener(v -> presenter.onDateDialogSelected(from));
        from.setText(trip.getFrom().format(dateFormatter));

        to = findViewById(R.id.to_edit);
        to.setOnClickListener(v -> presenter.onDateDialogSelected(to));
        to.setText(trip.getTo().format(dateFormatter));

        destination = findViewById(R.id.destination_edit);
        destination.setText(trip.getLocation());

        flightNumber = findViewById(R.id.flight_number_edit);
        flightNumber.setText(trip.getFlightNumber());

        departureTime = findViewById(R.id.departure_edit);
        departureTime.setText(trip.getDepartureTime().format(dateTimeFormatter));
        departureTime.setOnClickListener(v -> presenter.onDateTimeDialogSelected(departureTime));

        transportSpinner = findViewById(R.id.transport_method_edit);
        transportSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TravelMethod.values()));
        transportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                travelMethod = (TravelMethod) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing :)
            }
        });
        transportSpinner.setSelection(trip.getTravelMethod().ordinal());
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }


    @Override
    public void showDateDialog(EditText inputView) {
        LocalDate shownDate = DateUtils.parseDate(inputView);
        if (shownDate == null) {
            shownDate = LocalDate.now();
        }

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            LocalDate chosenDate = LocalDate.of(year, month + 1, dayOfMonth);
            inputView.setText(chosenDate.format(dateFormatter));
        };

        new DatePickerDialog(this, dateSetListener,
                shownDate.getYear(),
                shownDate.getMonthValue() - 1,
                shownDate.getDayOfMonth())
                .show();
    }

    @Override
    public void showDateTimeDialog(EditText inputView) {
        LocalDateTime shownDateTime = DateUtils.parseDateTime(inputView);
        if (shownDateTime == null) {
            shownDateTime = LocalDateTime.now();
        }
        dateTimeBuilder = new StringBuilder();

        LocalDateTime finalShownDateTime = shownDateTime;
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {

            LocalDate chosenDate = LocalDate.of(year, month + 1, dayOfMonth);
            String dateString = chosenDate.format(dateFormatter);
            dateTimeBuilder.append(dateString).append(" ");

            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
                LocalDateTime chosenTime = LocalDateTime.of(finalShownDateTime.getYear(), finalShownDateTime.getMonthValue(), finalShownDateTime.getDayOfMonth(), hour, minute);
                String timeString = chosenTime.format(timeFormatter);
                dateTimeBuilder.append(timeString);
                inputView.setText(dateTimeBuilder.toString());
            };

            new TimePickerDialog(this, timeSetListener,
                    finalShownDateTime.getHour(),
                    finalShownDateTime.getMinute(),
                    false).show();
        };
        new DatePickerDialog(this, dateSetListener,
                shownDateTime.getYear(),
                shownDateTime.getMonthValue() - 1,
                shownDateTime.getDayOfMonth()).show();
    }


    @Override
    public void setErrorMessage(EditText field, String message) {
        field.setError(message);
    }

    @Override
    public void launchDetailsActivity(Trip trip) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("trip", trip);
        startActivity(intent);
    }

    @Override
    public void launchDetailsActivityOnBack() {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("trip", this.trip);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        presenter.onValidationSuccess(from, to, departureTime, destination, travelMethod, flightNumber);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        presenter.onValidationErrors(errors);
    }

    @Override
    public void showSuccessMessage() {
        Toast.makeText(this, "Trip edited successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessages(List<ValidationError> errors) {
        Toast.makeText(this, "Error updating trip", Toast.LENGTH_SHORT).show();
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
