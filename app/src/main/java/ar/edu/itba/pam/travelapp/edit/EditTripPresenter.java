package ar.edu.itba.pam.travelapp.edit;

import android.os.AsyncTask;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ar.edu.itba.pam.travelapp.di.newtrip.createtrip.NewTripContainer;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;

import static ar.edu.itba.pam.travelapp.utils.DateUtils.parseDate;
import static ar.edu.itba.pam.travelapp.utils.DateUtils.parseDateTime;

public class EditTripPresenter {

    private final TripRepository tripRepository;
    private final WeakReference<EditTripView> view;
    private final Trip trip;

    public EditTripPresenter(final EditTripView view, final NewTripContainer newTripContainer, Trip trip) {
        this.tripRepository = newTripContainer.getTripRepository();
        this.view = new WeakReference<>(view);
        this.trip = trip;
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
        if(toDate != null) {
            if(toDate.isBefore(fromDate)) {
                if (view.get() != null) {
                    view.get().setErrorMessage(to, "Trip end date can't be before its start date");
                }
                return;
            }
        }
        if(departureDateTime != null) {
            if (departureDateTime.toLocalDate().isBefore(fromDate)) {
                if (view.get() != null) {
                    view.get().setErrorMessage(departureTime, "Departure time can't be before the trip's start date");
                }
                return;
            }
        }

        updateTrip(destination.getText().toString(), fromDate, toDate, travelMethod, flightNumber.getText().toString(), departureDateTime);
    }

    private void updateTrip(String destination, LocalDate fromDate, LocalDate toDate, TravelMethod travelMethod, String flightNumber, LocalDateTime departureDateTime) {
        trip.setLocation(destination);
        trip.setFrom(fromDate);
        trip.setTo(toDate);
        trip.setTravelMethod(travelMethod);
        if (flightNumber != null && flightNumber.length() > 0) {
            trip.setFlightNumber(flightNumber);
        }
        if (departureDateTime != null) {
            trip.setDepartureTime(departureDateTime);
        }
        AsyncTask.execute(() -> {
            tripRepository.updateTrip(trip);
        });
        if (view.get() != null) {
            view.get().showSuccessMessage();
            view.get().launchDetailsActivity(trip);
        }
    }


    public void onValidationErrors(List<ValidationError> errors) {
        if (view.get() != null) {
            view.get().showErrorMessages(errors);
        }
    }

    public void onCancel() {
        if (view.get() != null) {
            view.get().launchDetailsActivityOnBack();
        }
    }

    public void onLocationModified(EditText destination) {
        if (view.get() != null) {
            view.get().launchAutocompleteActivity(destination.getText().toString());
        }
    }
}
