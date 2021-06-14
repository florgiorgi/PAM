package ar.edu.itba.pam.travelapp.newtrip;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.di.newtrip.NewTripContainerLocator;
import ar.edu.itba.pam.travelapp.main.MainActivity;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CreateTripActivity extends AppCompatActivity implements Validator.ValidationListener, CreateTripView {

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
    private Button submitButton;
    private Validator validator;

    private CreateTripPresenter presenter;

    private StringBuilder dateTimeBuilder;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private TravelMethod travelMethod;

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
            this.presenter = new CreateTripPresenter(this,
                    NewTripContainerLocator.locateComponent(this));
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    private void initView() {
        this.destination = findViewById(R.id.destination_input);
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
    }

    @Override
    public void showDateDialog(EditText inputView) {
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

    @Override
    public void showDateTimeDialog(EditText inputView) {
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
        Toast.makeText(this, "Trip created successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessages(List<ValidationError> errors) {
        Toast.makeText(this, "Error creating trip", Toast.LENGTH_SHORT).show();
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

    @Override
    public void setErrorMessage(EditText field, String message) {
        field.setError(message);
    }

    @Override
    public void launchMainActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}
