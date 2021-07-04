package ar.edu.itba.pam.travelapp;

import android.content.Context;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.newtrip.createtrip.CreateTripPresenter;
import ar.edu.itba.pam.travelapp.newtrip.createtrip.CreateTripView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTripPresenterTest {
    private Context applicationContext;
    private CreateTripView view;
    private TripRepository tripRepository;
    private EditText editText;

    private CreateTripPresenter presenter;

    @Before
    public void setup() {
        this.applicationContext = mock(Context.class);
        this.view = mock(CreateTripView.class);
        this.tripRepository = mock(TripRepository.class);
        this.editText = mock(EditText.class);

        this.presenter = new CreateTripPresenter(view, tripRepository, applicationContext);
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
