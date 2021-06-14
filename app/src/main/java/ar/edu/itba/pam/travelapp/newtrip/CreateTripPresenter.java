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

import ar.edu.itba.pam.travelapp.di.newtrip.NewTripContainer;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import io.reactivex.disposables.Disposable;

public class CreateTripPresenter {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final TripRepository tripRepository;
    private final WeatherRepository weatherRepository;
    private final WeakReference<CreateTripView> view;
    private final AndroidSchedulerProvider schedulerProvider;

    private Trip trip;
    private Disposable disposable;

    public CreateTripPresenter(final CreateTripView view, final NewTripContainer container) {
        this.tripRepository = container.getTripRepository();
        this.view = new WeakReference<>(view);
        this.schedulerProvider = (AndroidSchedulerProvider) container.getSchedulerProvider();
        this.weatherRepository = container.getWeatherRepository();
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
        this.trip = new Trip(destination.getText().toString(), fromDate, toDate, travelMethod, departureDateTime, flightNumber.getText().toString());
        createTrip(trip);
//        fetchLocationKey();
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

    private void fetchLocationKey() {
        this.disposable = weatherRepository.findFirstMatchCity(trip.getLocation())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onCityReceived, error -> {
                    if (view.get() != null) {
                        view.get().onCityError();
                    }
                });
    }

    private void onCityReceived(City city) {
        System.out.println("----city: " + city);
        System.out.println("--------city key: " + city.getKey());
        trip.setLocationKey(city.getKey());
    }

    public void onViewDetached() {
        disposable.dispose();
    }
}
