package ar.edu.itba.pam.travelapp.main.trips;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ar.edu.itba.pam.travelapp.R;

public class CreateTripActivity extends AppCompatActivity {

    private EditText flightNumber;
    private EditText from;
    private EditText to;
    private EditText destination;
    private EditText departureTime;
    private Spinner travelMethod;
    private Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        this.departureTime = findViewById(R.id.date_time_of_departure_input);
        this.submitButton = findViewById(R.id.create_btn);
        this.travelMethod = findViewById(R.id.transport_spinner);
        this.destination = findViewById(R.id.destination_input);
        this.from = findViewById(R.id.from_input);
        this.to = findViewById(R.id.to_input);
        this.flightNumber = findViewById(R.id.flight_number_input);

        departureTime.setShowSoftInputOnFocus(false);
        departureTime.setOnClickListener(view -> showDateTimeDialog(departureTime));
    }

    private void showDateTimeDialog(EditText departureTime) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                departureTime.setText(dateFormat.format(calendar.getTime()));
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

}
