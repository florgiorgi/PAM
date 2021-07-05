package ar.edu.itba.pam.travelapp.newtrip.autocomplete;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;

public interface AutocompleteView {
    void onCityError(Throwable error);

    void bind(List<City> cities);

    void onClickConfirm();

//    void showDateTimeDialog(EditText input);

//    void showSuccessMessage();

//    void showErrorMessages(List<ValidationError> errors);

//    void setErrorMessage(EditText field, String message);

//    void launchMainActivity();
}
