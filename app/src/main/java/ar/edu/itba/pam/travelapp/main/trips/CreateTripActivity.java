package ar.edu.itba.pam.travelapp.main.trips;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.MainActivity;
import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

public class CreateTripActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    private EditText from;

    @NotEmpty
    private EditText to;

    @NotEmpty
    @Length(max = 20)
    private EditText destination;

    private EditText flightNumber;

    private EditText departureTime;

    private Spinner travelMethodSpinner;
    private TravelMethod travelMethod;

    private Button submitButton;

    private Validator validator;

    private Calendar fromCalendar;
    private Calendar toCalendar;

    private Calendar departureTimeCalendar;
    private boolean hasDepartureTime;

    private AppDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        initView();

        // Get database Instance
        database = AppDatabase.getInstance(getApplicationContext());

        // Setup input validations
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void initView() {

        // Spinner
        this.travelMethodSpinner = findViewById(R.id.transport_spinner);
        this.travelMethodSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TravelMethod.values()));
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

        this.hasDepartureTime = false;

        this.destination = findViewById(R.id.destination_input);
        this.flightNumber = findViewById(R.id.flight_number_input);

        // From Date
        this.from = findViewById(R.id.from_input);
        this.from.setShowSoftInputOnFocus(false);
        this.fromCalendar = Calendar.getInstance();
        this.from.setOnClickListener(view -> showDateDialog(from, fromCalendar));

        // To Date
        this.to = findViewById(R.id.to_input);
        this.to.setShowSoftInputOnFocus(false);
        this.toCalendar = Calendar.getInstance();
        this.to.setOnClickListener(view -> showDateDialog(to, toCalendar));

        // Departure date and time
        this.departureTime = findViewById(R.id.date_time_of_departure_input);
        this.departureTime.setShowSoftInputOnFocus(false);
        this.departureTimeCalendar = Calendar.getInstance();
        this.departureTime.setOnClickListener(view -> showDateTimeDialog(departureTime));

        // Submit button
        this.submitButton = findViewById(R.id.create_btn);
        this.submitButton.setOnClickListener(view -> validator.validate());
    }

    private void showDateDialog(EditText inputView, Calendar outputCalendar) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            outputCalendar.set(Calendar.YEAR, year);
            outputCalendar.set(Calendar.MONTH, month);
            outputCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            inputView.setText(dateFormat.format(outputCalendar.getTime()));
        };

        new DatePickerDialog(CreateTripActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showDateTimeDialog(EditText inputView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            departureTimeCalendar.set(Calendar.YEAR, year);
            departureTimeCalendar.set(Calendar.MONTH, month);
            departureTimeCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
                departureTimeCalendar.set(Calendar.HOUR_OF_DAY, hour);
                departureTimeCalendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                this.hasDepartureTime = true;
                inputView.setText(dateFormat.format(departureTimeCalendar.getTime()));
            };

            new TimePickerDialog(CreateTripActivity.this, timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false).show();
        };
        new DatePickerDialog(CreateTripActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void createTrip() {

        if (!this.hasDepartureTime) {
            this.departureTimeCalendar = null;
        }

        Trip trip = new Trip(this.destination.getText().toString(), fromCalendar, toCalendar, travelMethod, flightNumber.getText().toString(), departureTimeCalendar);
        // Can't access db on Main Thread
        AsyncTask.execute(() -> {
            database.tripDao().insert(trip);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Validation Success", Toast.LENGTH_SHORT).show();
        createTrip();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(this, "Validation Failed", Toast.LENGTH_SHORT).show();
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }
}
