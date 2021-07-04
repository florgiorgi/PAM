package ar.edu.itba.pam.travelapp.edit;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;

import static ar.edu.itba.pam.travelapp.utils.DateUtils.parseDate;
import static ar.edu.itba.pam.travelapp.utils.DateUtils.parseDateTime;

public class EditTripPresenter {
    private final Context applicationContext;
    private final Trip trip;
    private final TripRepository tripRepository;
    private final WeakReference<EditTripView> view;

    public EditTripPresenter(Context applicationContext, final EditTripView view, final TripRepository tripRepository, Trip trip) {
        this.applicationContext = applicationContext;
        this.tripRepository = tripRepository;
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
        } else {
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

        updateTrip(tripName.getText().toString(), destination.getText().toString(), fromDate, toDate, travelMethod, flightNumber.getText().toString(), departureDateTime, cityKey);
    }

    private void updateTrip(String tripName, String destination, LocalDate fromDate, LocalDate toDate, TravelMethod travelMethod, String flightNumber, LocalDateTime departureDateTime, String cityKey) {
        trip.setTripName(tripName);
        trip.setLocationKey(cityKey);
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
        AsyncTask.execute(() -> tripRepository.updateTrip(trip));
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
