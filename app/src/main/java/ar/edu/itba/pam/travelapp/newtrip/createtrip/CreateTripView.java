package ar.edu.itba.pam.travelapp.newtrip.createtrip;

import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface CreateTripView {
    void launchAutocompleteActivity(String city);

    void showDateDialog(EditText input);

    void showDateTimeDialog(EditText input);

    void showSuccessMessage();

    void showErrorMessages(List<ValidationError> errors);

    void setErrorMessage(EditText field, String message);

    void launchMainActivity();
}
