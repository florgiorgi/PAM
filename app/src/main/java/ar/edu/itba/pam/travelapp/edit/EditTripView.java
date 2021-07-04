package ar.edu.itba.pam.travelapp.edit;

import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.trip.Trip;

public interface EditTripView {

    void showDateDialog(EditText input);

    void showDateTimeDialog(EditText input);

    void showSuccessMessage();

    void showErrorMessages(List<ValidationError> errors);

    void setErrorMessage(EditText field, String message);

    void launchDetailsActivity(Trip trip);

    void launchDetailsActivityOnBack();

    void launchAutocompleteActivity(String city);
}
