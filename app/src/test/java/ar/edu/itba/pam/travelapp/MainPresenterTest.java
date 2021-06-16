package ar.edu.itba.pam.travelapp;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import ar.edu.itba.pam.travelapp.landing.storage.FtuStorage;
import ar.edu.itba.pam.travelapp.main.MainPresenter;
import ar.edu.itba.pam.travelapp.main.MainView;
import ar.edu.itba.pam.travelapp.main.storage.NightModeStorage;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;
import io.reactivex.Flowable;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    private MainView view;
    private FtuStorage storage;
    private NightModeStorage nightModeStorage;
    private TripRepository tripRepository;
    private SchedulerProvider provider;

    private LocalDate now;

    private MainPresenter presenter;

    @Before
    public void setup() {
        view = mock(MainView.class);
        storage = mock(FtuStorage.class);
        nightModeStorage = mock(NightModeStorage.class);
        provider = new SchedulerProviderTest();
        tripRepository = mock(TripRepository.class);
        now = LocalDate.now();

        presenter = new MainPresenter(view, storage, nightModeStorage, tripRepository, provider);
    }

    @Test
    public void givenTripWasClickedThenLaunchDetailsScreen() {
        Trip trip = new Trip(1, "Buenos Aires", now, now, TravelMethod.Airplane, now.atStartOfDay(), "123");
        presenter.onTripClicked(trip);
        verify(view).launchTripDetail(trip);
    }

    @Test
    public void givenTheViewWasAttachedWhenFtuMustBeShownThenShowTheLandingScreen() {
        when(storage.isActive()).thenReturn(true);
        final Trip[] tripArray = {mock(Trip.class), mock(Trip.class)};
        final List<Trip> trips = Arrays.asList(tripArray);
        final Flowable<List<Trip>> tripsFlowable = Flowable.just(trips);
        doReturn(tripsFlowable).when(tripRepository).getTrips();
        presenter.onViewAttached();
        verify(view).launchFtu();
    }

    @Test
    public void givenTheViewWasAttachedWhenErrorFetchingTripsOccurredThenShowErrorMessage() {
        when(storage.isActive()).thenReturn(true);

        doReturn(
                Flowable.fromCallable(() -> {
                    throw new Exception("Broken");
                })
        ).when(tripRepository).getTrips();

        presenter.onViewAttached();
        verify(view).onTripsError();
    }

    @Test
    public void givenTheViewWasAttachedWhenFtuMustBeShownThenDeactivateFtuScreen() {
        when(storage.isActive()).thenReturn(true);
        final Trip[] tripArray = {mock(Trip.class), mock(Trip.class)};
        final List<Trip> trips = Arrays.asList(tripArray);
        final Flowable<List<Trip>> tripsFlowable = Flowable.just(trips);
        doReturn(tripsFlowable).when(tripRepository).getTrips();
        presenter.onViewAttached();
        verify(storage).deactivate();
    }

    @Test
    public void givenTheViewWasAttachedWhenFtuMustNotBeShownThenDontDeactivateIt() {
        when(storage.isActive()).thenReturn(false);
        final Trip[] tripArray = {mock(Trip.class), mock(Trip.class)};
        final List<Trip> trips = Arrays.asList(tripArray);
        final Flowable<List<Trip>> tripsFlowable = Flowable.just(trips);
        doReturn(tripsFlowable).when(tripRepository).getTrips();
        presenter.onViewAttached();
        verify(storage, never()).deactivate();
    }

    @Test
    public void givenTheViewWasAttachedThenDestroyNightModeFtuStorage() {
        presenter.onViewDestroyed();
        verify(nightModeStorage).setDefaultNightMode();
    }

    @Test
    public void givenTripsScreenSelectedThenShowTripsScreen() {
        presenter.onTripsScreenSelected();
        verify(view).showTripsScreen();
    }

    @Test
    public void givenHistoryScreenSelectedThenShowHistoryScreen() {
        presenter.onHistoryScreenSelected();
        verify(view).showHistoryScreen();
    }

    @Test
    public void givenConfigurationScreenSelectedThenShowConfigurationScreen() {
        presenter.onConfigurationScreenSelected();
        verify(view).showConfigurationScreen();
    }

    @Test
    public void givenCreateTripButtonIsClickedThenShowCreateTripScreen() {
        presenter.onCreateTripClicked();
        verify(view).launchCreateTrip();
    }
}
