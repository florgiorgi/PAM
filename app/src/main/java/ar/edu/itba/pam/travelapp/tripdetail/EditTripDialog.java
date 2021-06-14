package ar.edu.itba.pam.travelapp.tripdetail;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.newtrip.CreateTripActivity;

public class EditTripDialog extends DialogFragment {

    private EditTripDialogListener listener;
    private Context context;

    private EditText from;
    private EditText to;
    private EditText destination;
    private EditText flightNumber;
    private EditText departureTime;
    private Spinner transportSpinner;
    private TravelMethod travelMethod;

    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDateTime departureDate;

    private StringBuilder dateTimeBuilder;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_trip_dialog, null);

        builder.setView(view)
                .setTitle("Edit Trip")
                .setPositiveButton("Confirm", (dialog, id) -> {
                    if (validateInput()) {
                        Trip trip = new Trip(destination.getText().toString(), fromDate, toDate, travelMethod, departureDate, flightNumber.getText().toString());
                        listener.editTrip(trip);
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> System.out.println("Cancelled"));

        from = view.findViewById(R.id.from_edit);
        from.setOnClickListener(v -> showDateDialog(from));
        to = view.findViewById(R.id.to_edit);
        to.setOnClickListener(v -> showDateDialog(to));
        destination = view.findViewById(R.id.destination_edit);
        flightNumber = view.findViewById(R.id.flight_number_edit);
        departureTime = view.findViewById(R.id.departure_edit);
        departureTime.setOnClickListener(v -> showDateTimeDialog(departureTime));
        transportSpinner = view.findViewById(R.id.transport_method_edit);
        transportSpinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, TravelMethod.values()));
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

        Trip trip = listener.provideTrip();
        destination.setText(trip.getLocation());
        from.setText(trip.getFrom().format(dateFormatter));
        to.setText(trip.getTo().format(dateFormatter));
        flightNumber.setText(trip.getFlightNumber());
        departureTime.setText(trip.getDepartureTime().format(dateTimeFormatter));
        transportSpinner.setSelection(trip.getTravelMethod().ordinal());

        return builder.create();
    }

    private void showDateDialog(EditText inputView) {
        LocalDate shownDate = parseDate(inputView);
        if (shownDate == null) {
            shownDate = LocalDate.now();
        }

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            LocalDate chosenDate = LocalDate.of(year, month + 1, dayOfMonth);
            inputView.setText(chosenDate.format(dateFormatter));
        };

        new DatePickerDialog(context, dateSetListener,
                shownDate.getYear(),
                shownDate.getMonthValue() - 1,
                shownDate.getDayOfMonth())
                .show();
    }

    public void showDateTimeDialog(EditText inputView) {
        LocalDateTime shownDateTime = parseDateTime(inputView);
        if (shownDateTime == null) {
            shownDateTime = LocalDateTime.now();
        }
        dateTimeBuilder = new StringBuilder();

        //Calendar calendar = Calendar.getInstance();
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

            new TimePickerDialog(context, timeSetListener,
                    finalShownDateTime.getHour(),
                    finalShownDateTime.getMinute(),
                    false).show();
        };
        new DatePickerDialog(context, dateSetListener,
                shownDateTime.getYear(),
                shownDateTime.getMonthValue() - 1,
                shownDateTime.getDayOfMonth()).show();
    }

    private boolean validateInput() {
        boolean error = false;
        LocalDate toDate = parseDate(to);
        LocalDate fromDate = parseDate(from);
        if (destination.getText() == null || destination.getText().length() == 0) {
            destination.setError("Field is required");
            error = true;
        }
        if (destination.getText() != null && destination.getText().length() > 20) {
            destination.setError("Max 20 characters");
            error = true;
        }
        if (toDate == null) {
            to.setError("Field is required");
            error = true;
        }
        if (fromDate == null) {
            from.setError("Field is required");
            error = true;
        }
        return !error;
    }

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        try {
            listener = (EditTripDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement EditTripDialogListener");
        }
    }

    public interface EditTripDialogListener {
        void editTrip(Trip trip);

        Trip provideTrip();
    }
}


