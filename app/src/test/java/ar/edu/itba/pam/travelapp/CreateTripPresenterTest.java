package ar.edu.itba.pam.travelapp;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.newtrip.CreateTripPresenter;
import ar.edu.itba.pam.travelapp.newtrip.CreateTripView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTripPresenterTest {

    private CreateTripView view;
    private TripRepository tripRepository;
    private EditText editText;

    private CreateTripPresenter presenter;

    @Before
    public void setup() {
        view = mock(CreateTripView.class);
        tripRepository = mock(TripRepository.class);
        editText = mock(EditText.class);

        presenter = new CreateTripPresenter(view, tripRepository);
    }

    @Test
    public void givenDateDialogIsSelectedThenShowDateDialogScreen() {
        presenter.onDateDialogSelected(editText);
        verify(view).showDateDialog(editText);
    }

    @Test
    public void givenDateTimeDialogIsSelectedThenShowDateTimeDialogScreen() {
        presenter.onDateTimeDialogSelected(editText);
        verify(view).showDateTimeDialog(editText);
    }

    @Test
    public void givenValidationHasErrorsThenShowErrorMessages() {
        presenter.onValidationErrors(null);
        verify(view).showErrorMessages(null);
    }

}
