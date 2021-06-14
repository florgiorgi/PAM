package ar.edu.itba.pam.travelapp.newtrip;

import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface CreateTripView {

    void showDateDialog(EditText input);

    void showDateTimeDialog(EditText input);

    void showSuccessMessage();

    void showErrorMessages(List<ValidationError> errors);

    void setErrorMessage(EditText field, String message);

    void launchMainActivity();

    void onCityError();
}
