package ar.edu.itba.pam.travelapp.newtrip;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
import java.time.format.DateTimeParseException;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.main.MainActivity;
import ar.edu.itba.pam.travelapp.model.AppDatabase;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripMapper;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.trip.TripRoomRepository;

@RequiresApi(api = Build.VERSION_CODES.O)
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

    private AppDatabase database;
    private TripRepository tripRepository;

    private StringBuilder dateTimeBuilder;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        initView();

        // Get database Instance
        database = AppDatabase.getInstance(getApplicationContext());
        tripRepository = new TripRoomRepository(database.tripDao(), new TripMapper());

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

        this.destination = findViewById(R.id.destination_input);
        this.flightNumber = findViewById(R.id.flight_number_input);

        // From Date
        this.from = findViewById(R.id.from_input);
        this.from.setShowSoftInputOnFocus(false);
        this.from.setOnClickListener(view -> showDateDialog(from));

        // To Date
        this.to = findViewById(R.id.to_input);
        this.to.setShowSoftInputOnFocus(false);
        this.to.setOnClickListener(view -> showDateDialog(to));

        // Departure date and time
        this.departureTime = findViewById(R.id.date_time_of_departure_input);
        this.departureTime.setShowSoftInputOnFocus(false);
        this.departureTime.setOnClickListener(view -> showDateTimeDialog(departureTime));

        // Submit button
        this.submitButton = findViewById(R.id.create_btn);
        this.submitButton.setOnClickListener(view -> validator.validate());
    }

    private void showDateDialog(EditText inputView) {
        LocalDate date = LocalDate.now();
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

    private void showDateTimeDialog(EditText inputView) {
        LocalDateTime now = LocalDateTime.now();
        dateTimeBuilder = new StringBuilder();

        //Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {

            LocalDate chosenDate = LocalDate.of(year, month + 1, dayOfMonth);
            String dateString = chosenDate.format(dateFormatter);
            dateTimeBuilder.append(dateString).append(" ");

            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
                LocalDateTime chosenTime = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), hour, minute);
                String timeString = chosenTime.format(timeFormatter);
                dateTimeBuilder.append(timeString);
                inputView.setText(dateTimeBuilder.toString());
            };

            new TimePickerDialog(CreateTripActivity.this, timeSetListener,
                    now.getHour(),
                    now.getMinute(),
                    false).show();
        };
        new DatePickerDialog(CreateTripActivity.this, dateSetListener,
                now.getYear(),
                now.getMonthValue() - 1,
                now.getDayOfMonth()).show();
    }

    private void createTrip() {
        LocalDate fromDate = parseDate(from);
        LocalDate toDate = parseDate(to);
        LocalDateTime departureDateTime = parseDateTime(departureTime);

        if (fromDate == null) {
            ((EditText) from).setError("Invalid date format");
        }
        if (toDate == null) {
            ((EditText) to).setError("Invalid date format");
        }
        if (fromDate == null || toDate == null) {
            return;
        }

        Trip trip = new Trip(this.destination.getText().toString(), fromDate, toDate, travelMethod, departureDateTime, flightNumber.getText().toString());
        AsyncTask.execute(() -> {
            tripRepository.insertTrip(trip);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }

    // Parse 'dd/MM/yyyy' String to LocalDate
    private LocalDate parseDate(EditText inputField) {
        String dateInput = inputField.getText().toString();
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(dateInput, dateFormatter);
        } catch (DateTimeParseException e) {
            parsedDate = null;
        }
        return parsedDate;
    }

    // Parse 'dd/MM/yyyy HH:mm' String to LocalDate
    private LocalDateTime parseDateTime(EditText inputField) {
        String dateInput = inputField.getText().toString();
        LocalDateTime parsedDateTime;
        try {
            parsedDateTime = LocalDateTime.parse(dateInput, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            parsedDateTime = null;
        }
        return parsedDateTime;
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Trip created successfully", Toast.LENGTH_SHORT).show();
        createTrip();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(this, "Error creating trip", Toast.LENGTH_SHORT).show();
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
