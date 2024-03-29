package ar.edu.itba.pam.travelapp.newtrip.createtrip;

import android.app.Activity;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.di.newtrip.createtrip.NewTripContainer;
import ar.edu.itba.pam.travelapp.di.newtrip.createtrip.NewTripContainerLocator;
import ar.edu.itba.pam.travelapp.main.MainActivity;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.newtrip.autocomplete.AutocompleteActivity;
import ar.edu.itba.pam.travelapp.utils.DateUtils;


public class CreateTripActivity extends AppCompatActivity implements Validator.ValidationListener, CreateTripView {
    public static final int AUTOCOMPLETE = 99;

    @NotEmpty
    private EditText from;
    @NotEmpty
    private EditText to;
    @NotEmpty
    @Length(max = 20)
    private EditText tripName;
    private EditText destination;
    private String cityKey;
    private EditText flightNumber;
    private EditText departureTime;
    private Spinner travelMethodSpinner;
    private TravelMethod travelMethod;
    private Button submitButton;
    private Validator validator;

    private CreateTripPresenter presenter;

    private StringBuilder dateTimeBuilder;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        createPresenter();
        initView();
        initValidator();
    }

    private void initValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void createPresenter() {
        Object possibleCreateTripPresenter = getLastNonConfigurationInstance();
        if (possibleCreateTripPresenter instanceof CreateTripPresenter) {
            this.presenter = (CreateTripPresenter) possibleCreateTripPresenter;
        }
        if (this.presenter == null) {
            NewTripContainer container = NewTripContainerLocator.locateComponent(this);
            this.presenter = new CreateTripPresenter(this, container.getTripRepository(),
                    container.getApplicationContext());
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    private void initView() {
        this.tripName = findViewById(R.id.tripName_input);
        this.destination = findViewById(R.id.destination_input);
        this.destination.setOnClickListener(view -> presenter.onDestinationSelected(destination));
        this.flightNumber = findViewById(R.id.flight_number_input);
        this.from = findViewById(R.id.from_input);
        this.from.setShowSoftInputOnFocus(false);
        this.from.setOnClickListener(view -> presenter.onDateDialogSelected(from));
        this.to = findViewById(R.id.to_input);
        this.to.setShowSoftInputOnFocus(false);
        this.to.setOnClickListener(view -> showDateDialog(to));
        this.departureTime = findViewById(R.id.date_time_of_departure_input);
        this.departureTime.setShowSoftInputOnFocus(false);
        this.departureTime.setOnClickListener(view -> presenter.onDateTimeDialogSelected(departureTime));
        this.submitButton = findViewById(R.id.create_btn);
        this.submitButton.setOnClickListener(view -> validator.validate());
        this.travelMethodSpinner = findViewById(R.id.transport_spinner);
        this.travelMethodSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, TravelMethod.values()));
        this.travelMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                travelMethod = (TravelMethod) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing :)
            }
        });
    }

    @Override
    public void launchAutocompleteActivity(String city) {
        Intent intent = new Intent(this, AutocompleteActivity.class);
        intent.putExtra("city", city);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityForResult(intent, AUTOCOMPLETE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    this.cityKey = data.getStringExtra("cityKey");
                    this.destination.setText(data.getStringExtra("city"));
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data != null) {
                    this.destination.setText(data.getStringExtra("city"));
                }
            }
        }
    }

    @Override
    public void showDateDialog(EditText inputView) {
        LocalDate date = null;
        if (inputView.getText().length() > 0 && DateUtils.parseDate(inputView) != null) {
            date = DateUtils.parseDate(inputView);
        } else {
            if (inputView.equals(to) && DateUtils.parseDate(from) != null) {
                date = DateUtils.parseDate(from);
            }
        }
        if (date == null) {
            date = LocalDate.now();
        }
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            LocalDate chosenDate = LocalDate.of(year, month + 1, dayOfMonth);
            inputView.setText(chosenDate.format(dateFormatter));
        };

        new DatePickerDialog(CreateTripActivity.this, dateSetListener,
                date.getYear(),
                date.getMonthValue() - 1,
                date.getDayOfMonth())
                .show();
    }

    @Override
    public void showDateTimeDialog(EditText inputView) {
        LocalDateTime now = null;
        if (inputView.getText().length() > 0 && DateUtils.parseDateTime(inputView) != null) {
            now = DateUtils.parseDateTime(inputView);
        } else {
            if (inputView.equals(departureTime) && DateUtils.parseDate(from) != null) {
                now = DateUtils.parseDate(from).atStartOfDay();
            }
        }
        if (now == null) {
            now = LocalDateTime.now();
        }

        dateTimeBuilder = new StringBuilder();
        LocalDateTime finalNow = now;
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {

            LocalDate chosenDate = LocalDate.of(year, month + 1, dayOfMonth);
            String dateString = chosenDate.format(dateFormatter);
            dateTimeBuilder.append(dateString).append(" ");

            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
                LocalDateTime chosenTime = LocalDateTime.of(finalNow.getYear(),
                        finalNow.getMonthValue(), finalNow.getDayOfMonth(), hour, minute);
                String timeString = chosenTime.format(timeFormatter);
                dateTimeBuilder.append(timeString);
                inputView.setText(dateTimeBuilder.toString());
            };

            new TimePickerDialog(CreateTripActivity.this, timeSetListener,
                    finalNow.getHour(),
                    finalNow.getMinute(),
                    false).show();
        };
        new DatePickerDialog(CreateTripActivity.this, dateSetListener,
                now.getYear(),
                now.getMonthValue() - 1,
                now.getDayOfMonth()).show();
    }


    @Override
    public void onValidationSucceeded() {
        presenter.onValidationSuccess(tripName, from, to, departureTime, destination, travelMethod,
                flightNumber, cityKey);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        presenter.onValidationErrors(errors);
    }

    @Override
    public void showSuccessMessage() {
        Toast.makeText(this, this.getResources().getString(R.string.trip_created_successfully), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessages(List<ValidationError> errors) {
        Toast.makeText(this, this.getResources().getString(R.string.error_creating_trip), Toast.LENGTH_SHORT).show();
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setErrorMessage(EditText field, String message) {
        field.setError(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
