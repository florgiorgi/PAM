package ar.edu.itba.pam.travelapp.main;

import java.lang.ref.WeakReference;

import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import io.reactivex.disposables.Disposable;

public class MainPresenter {

    private AndroidSchedulerProvider schedulerProvider;
    private Disposable disposable;

    private final FtuStorage ftuStorage;
    private final WeakReference<MainView> view;
    private final TripRepository tripRepository;

    public MainPresenter(final FtuStorage ftuStorage, final TripRepository repository, final MainView view) {
        this.ftuStorage = ftuStorage;
        this.tripRepository = repository;
        this.view = new WeakReference<>(view);
        this.schedulerProvider = new AndroidSchedulerProvider();
    }

    public void onViewAttached() {

        if (ftuStorage.isActive()) {
            ftuStorage.deactivate();
            if (view.get() != null) {
                view.get().launchFtu();
            }
        }
    }

    private void fetchTrips() {
        this.disposable = tripRepository.getTrips()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(model -> {
                    if (view.get() != null) {

                        //todo get upcoming and history trips
                        //view.get().bindTrips(model);
                    }
                });
    }

    public void onTripsScreenSelected() {
        if (view.get() != null) {
            view.get().showTripsScreen();
        }
    }

    public void onHistoryScreenSelected() {
        if (view.get() != null) {
            view.get().showHistoryScreen();
        }
    }

    public void onConfigurationScreenSelected() {
        if (view.get() != null) {
            view.get().showConfigurationScreen();
        }
    }

    public void onViewDetached() {
        disposable.dispose();
    }

    public void onCreateTripClicked() {
        if (view.get() != null) {
            view.get().launchCreateTrip();
        }
    }
}
