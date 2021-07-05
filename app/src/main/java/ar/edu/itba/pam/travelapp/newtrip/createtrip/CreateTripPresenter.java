package ar.edu.itba.pam.travelapp.newtrip.createtrip;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;

public class CreateTripPresenter {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Context applicationContext;
    private final TripRepository tripRepository;
    private final WeakReference<CreateTripView> view;

    private Trip trip;

    public CreateTripPresenter(final CreateTripView view, final TripRepository tripRepository,
                               Context applicationContext) {
        this.tripRepository = tripRepository;
        this.view = new WeakReference<>(view);
        this.applicationContext = applicationContext;
    }

    private void createTrip(Trip trip) {
        AsyncTask.execute(() -> tripRepository.insertTrip(trip));
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

    public void onValidationSuccess(EditText tripName, EditText from, EditText to, EditText departureTime, EditText destination, TravelMethod travelMethod, EditText flightNumber, String cityKey) {
        if (destination.getText().toString().isEmpty()) {
            if (view.get() != null) {
                view.get().setErrorMessage(destination, applicationContext.getResources().getString(R.string.cannot_be_empty, "Destination"));
            }
            return;
        }

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

        if (toDate.isBefore(fromDate)) {
            if (view.get() != null) {
                view.get().setErrorMessage(to, "Trip end date can't be before its start date");
            }
            return;
        }
        if (departureDateTime != null) {
            if (departureDateTime.toLocalDate().isBefore(fromDate)) {
                if (view.get() != null) {
                    view.get().setErrorMessage(departureTime, "Departure time can't be before the trip's start date");
                }
                return;
            }
        }

        this.trip = new Trip(tripName.getText().toString(), destination.getText().toString(), fromDate, toDate, travelMethod, departureDateTime, flightNumber.getText().toString(), cityKey);
        createTrip(trip);
    }

    public void onValidationErrors(List<ValidationError> errors) {
        if (view.get() != null) {
            view.get().showErrorMessages(errors);
        }
    }

    public void onDestinationSelected(EditText inputWritten) {
        if (view.get() != null) {
            view.get().launchAutocompleteActivity(inputWritten.getText().toString());
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
