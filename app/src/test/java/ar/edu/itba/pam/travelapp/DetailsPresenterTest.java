package ar.edu.itba.pam.travelapp;

import android.os.AsyncTask;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.tripdetail.DetailsPresenter;
import ar.edu.itba.pam.travelapp.tripdetail.DetailsView;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DetailsPresenterTest {

    private DetailsView view;
    private ActivityRepository activityRepository;
    private Trip trip;
    private TripRepository tripRepository;
    private AndroidSchedulerProvider schedulerProvider;
    private WeatherRepository weatherRepository;
    private LocalDate now;

    private DetailsPresenter presenter;


    @Before
    public void setup() {
        now = LocalDate.now();
        view = mock(DetailsView.class);
        activityRepository = mock(ActivityRepository.class);
        trip = new Trip(1, "Buenos Aires", now, now, TravelMethod.Airplane, now.atStartOfDay(), "123");
        tripRepository = mock(TripRepository.class);
        schedulerProvider = mock(AndroidSchedulerProvider.class);
        weatherRepository = mock(WeatherRepository.class);

        presenter = new DetailsPresenter(view, trip, activityRepository, tripRepository, schedulerProvider, weatherRepository);
    }

    @Test
    public void givenDeleteTripButtonIsPressedThenShowConfirmDialog() {
        presenter.onDeleteTrip();
        verify(view).openConfirmDeleteDialog();
    }

    @Test
    public void givenEditTripButtonIsPressedThenShowEditTripActivity() {
        presenter.onEditTrip();
        verify(view).startEditTripActivity();
    }

    @Test
    public void givenNewActivityIsCreatedThenAddActivityToViewAndShowSuccessMessage() {
        presenter.onActivityCreate("New Activity", now);
        // todo: fix or remove
        verify(view).showNewActivitySuccessMessage();
    }

}
