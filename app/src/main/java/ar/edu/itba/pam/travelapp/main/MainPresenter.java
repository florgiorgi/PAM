package ar.edu.itba.pam.travelapp.main;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ar.edu.itba.pam.travelapp.landing.storage.FtuStorage;
import ar.edu.itba.pam.travelapp.main.storage.NightModeStorage;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;
import io.reactivex.disposables.Disposable;

public class MainPresenter {

    private final SchedulerProvider schedulerProvider;
    private Disposable disposable;

    private final FtuStorage ftuStorage;
    private final NightModeStorage nightModeStorage;
    private final TripRepository tripRepository;
    private final WeakReference<MainView> view;

    public MainPresenter(final MainView view, final FtuStorage ftuStorage, final NightModeStorage nightModeStorage,
                         final TripRepository tripRepository, final SchedulerProvider schedulerProvider) {
        this.ftuStorage = ftuStorage;
        this.nightModeStorage = nightModeStorage;
        this.tripRepository = tripRepository;
        this.view = new WeakReference<>(view);
        this.schedulerProvider = schedulerProvider;
    }

    public void onViewAttached() {
        if (ftuStorage.isActive()) {
            ftuStorage.deactivate();
            if (view.get() != null) {
                view.get().launchFtu();
            }
        }
        if (nightModeStorage.isActive()) {
            nightModeStorage.reactivateNightMode();
        }
        fetchTrips();
    }

    public void onViewDestroyed() {
        nightModeStorage.setDefaultNightMode();
    }

    private void fetchTrips() {
        this.disposable = tripRepository.getTrips()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onTripsReceived, error -> {
                    if (view.get() != null) {
                        view.get().onTripsError();
                    }
                });
    }

    private void onTripsReceived(final List<Trip> trips) {
        LocalDate today = LocalDate.now();
        List<Trip> upcoming = trips.stream().filter(t -> (t.getTo().isAfter(today) || t.getTo().isEqual(today))).collect(Collectors.toList());
        List<Trip> history = trips.stream().filter(t -> t.getTo().isBefore(today)).collect(Collectors.toList());
        List<Object> historyObjects = parsedHistoryTrips(history);
        if (view.get() != null) {
            view.get().bindUpcomingTrips(upcoming);
            view.get().bindHistoryTrips(historyObjects);
        }
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

    public void onTripClicked(Trip trip) {
        if (view.get() != null) {
            view.get().launchTripDetail(trip);
        }
    }

    private List<Object> parsedHistoryTrips(List<Trip> dataset) {
        Map<String, List<Trip>> tripsMap = new HashMap<>();
        Map<Trip, String> auxMap = new HashMap<>();

        //create map with trips as keys and their year as values
        for (Trip trip : dataset) {
            LocalDate date = trip.getFrom();
            String year = String.valueOf(date.getYear());
            auxMap.put(trip, year);
        }

        //create set with years based on the map's values
        SortedSet<String> yearsSet = new TreeSet<>(auxMap.values());

        //create map with year as key and a list of trips from that year as value
        for (String year : yearsSet) {
            List<Trip> trips = new ArrayList<>();
            for (Map.Entry e : auxMap.entrySet()) {
                if (e.getValue().equals(year)) {
                    trips.add((Trip) e.getKey());
                }
            }
            tripsMap.put(year, trips);
        }

        //reverse order the years in the set
        Set<String> auxSet = tripsMap.keySet();
        List list = new ArrayList(auxSet);
        Collections.sort(list, Collections.reverseOrder());
        Set<String> orderedYearsSet = new LinkedHashSet(list);

        //create list with years and trips at the same level to pass to the adapter
        List<Object> parsedData = new ArrayList<>();

        for (String k : orderedYearsSet) {
            parsedData.add(k);
            for (Trip t : tripsMap.get(k)) {
                parsedData.add(t);
            }
        }
        return parsedData;
    }
}
