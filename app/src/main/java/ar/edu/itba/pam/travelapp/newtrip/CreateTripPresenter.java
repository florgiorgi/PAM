package ar.edu.itba.pam.travelapp.newtrip;

import android.os.AsyncTask;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;

public class CreateTripPresenter {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final TripRepository tripRepository;
    private final WeakReference<CreateTripView> view;

    private Trip trip;

    public CreateTripPresenter(final CreateTripView view, final TripRepository tripRepository) {
        this.tripRepository = tripRepository;
        this.view = new WeakReference<>(view);
    }

    private void createTrip(Trip trip) {
        AsyncTask.execute(() -> {
            tripRepository.insertTrip(trip);
        });
        if (view.get() != null) {
            view.get().showSuccessMessage();
            view.get().launchMainActivity();
        }
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

    public void onValidationSuccess(EditText from, EditText to, EditText departureTime, EditText destination, TravelMethod travelMethod, EditText flightNumber) {
        LocalDate fromDate = parseDate(from);
        LocalDate toDate = parseDate(to);
        LocalDateTime departureDateTime = parseDateTime(departureTime);
        if (fromDate == null) {
            if (view.get() != null) {
                view.get().setErrorMessage(from, "Invalid date format");
            }
        }
        if (toDate == null) {
            if (view.get() != null) {
                view.get().setErrorMessage(to, "Invalid date format");
            }
        }
        if (fromDate == null || toDate == null) {
            return;
        }
        if (fromDate.isAfter(toDate)) {
            if (view.get() != null) {
                view.get().setErrorMessage(from, "Invalid date");
            }
            return;
        }
        this.trip = new Trip(destination.getText().toString(), fromDate, toDate, travelMethod, departureDateTime, flightNumber.getText().toString());
        createTrip(trip);
    }

    public void onValidationErrors(List<ValidationError> errors) {
        if (view.get() != null) {
            view.get().showErrorMessages(errors);
        }
    }

    public void onDateDialogSelected(EditText inputField) {
        if (view.get() != null) {
            view.get().showDateDialog(inputField);
        }
    }

    public void onDateTimeDialogSelected(EditText departureTime) {
        if (view.get() != null) {
            view.get().showDateTimeDialog(departureTime);
        }
    }
}
