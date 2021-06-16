package ar.edu.itba.pam.travelapp;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import ar.edu.itba.pam.travelapp.di.newtrip.NewTripContainer;
import ar.edu.itba.pam.travelapp.edit.EditTripPresenter;
import ar.edu.itba.pam.travelapp.edit.EditTripView;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditTripPresenterTest {

    private Trip trip;
    private NewTripContainer newTripContainer;
    private EditTripView view;
    private LocalDate now;
    private EditText editText;

    private EditTripPresenter presenter;

    @Before
    public void setup() {
        now = LocalDate.now();
        view = mock(EditTripView.class);
        trip = new Trip(1, "Buenos Aires", now, now, TravelMethod.Airplane, now.atStartOfDay(), "123");
        newTripContainer = mock(NewTripContainer.class);
        editText = mock(EditText.class);

        presenter = new EditTripPresenter(view, newTripContainer, trip);
    }

    @Test
    public void givenDateDialogIsSelectedThenShowDateDialog() {
        presenter.onDateDialogSelected(editText);
        verify(view).showDateDialog(editText);
    }

    @Test
    public void givenDateTimeDialogIsSelectedThenShowDateTimeDialog() {
        presenter.onDateTimeDialogSelected(editText);
        verify(view).showDateTimeDialog(editText);
    }

    @Test
    public void givenThatValidationHasErrorsThenShowErrorMessages() {
        presenter.onValidationErrors(null);
        verify(view).showErrorMessages(null);
    }

    @Test
    public void givenThatCancelButtonIsClickedThenLaunchDetailsActivity() {
        presenter.onCancel();
        verify(view).launchDetailsActivityOnBack();
    }

/*    @Test
    public void givenThatValidationHasNoErrorsThenEditTrip() {
        presenter.onValidationSuccess(editText, editText, editText, editText, TravelMethod.Airplane, editText);
        verify(view).showSuccessMessage();
    }*/

}
