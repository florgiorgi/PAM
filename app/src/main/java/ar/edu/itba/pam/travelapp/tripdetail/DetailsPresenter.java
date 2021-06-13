package ar.edu.itba.pam.travelapp.tripdetail;

import android.os.AsyncTask;
import android.os.Build;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import androidx.annotation.RequiresApi;

import ar.edu.itba.pam.travelapp.di.tripdetail.DetailsContainer;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.weather.forecast.Forecast;
import ar.edu.itba.pam.travelapp.model.weather.location.City;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import ar.edu.itba.pam.travelapp.utils.networking.NotFoundException;
import io.reactivex.disposables.Disposable;

public class DetailsPresenter {

    private final ActivityRepository activityRepository;
    private final WeatherRepository weatherRepository;
    private final WeakReference<DetailsView> view;
    private final AndroidSchedulerProvider schedulerProvider;
    private final Trip trip;
    private Disposable disposable;

    public DetailsPresenter(final DetailsView view, final Trip trip, final DetailsContainer container) {
        this.activityRepository = container.getActivityRepository();
        this.view = new WeakReference<>(view);
        this.schedulerProvider = (AndroidSchedulerProvider) container.getSchedulerProvider();
        this.weatherRepository = container.getWeatherRepository();
        this.trip = trip;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewAttached() {
        this.disposable = activityRepository.findByTripId(this.trip.getId())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onActivitiesReceived, error -> {
                    if (view.get() != null) {
                        view.get().showActivitiesErrorMessage();
                    }
                });
        fetchWeatherForecasts();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onActivitiesReceived(List<Activity> activities) {
        Set<LocalDate> datesSet = new LinkedHashSet<>();
        Map<LocalDate, List<Activity>> map = parseActivities(activities, datesSet);
        if (view.get() != null) {
            view.get().bindDataset(datesSet, map);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fetchWeatherForecasts() {
        String destination = trip.getLocation();
        String locationKey = "7894";
//        Optional<City> city = weatherRepository.findCity(destination); todo
//        if (!city.isPresent()) { fixme
//            throw new NotFoundException("City not found");
//        }
        this.disposable = weatherRepository.getForecastForCity(locationKey)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onForecastReceived, error -> {
                    if (view.get() != null) {
                        view.get().onForecastError();
                    }
                });
    }

    private void onNetworkConfigError(final Throwable throwable) {
        // todo: explain the error to the user
    }

    private void onForecastReceived(Forecast forecast) {
        if (view.get() != null) {
            view.get().bindForecastToDay(forecast);
        }
    }

    public void onViewDetached() {
        disposable.dispose();
    }

    public void onActivityCreate(String name, LocalDate date) {
        if (name != null && name.length() > 0) {
            AsyncTask.execute(() -> {
                Activity activity = new Activity(name, this.trip.getId(), date);
                this.activityRepository.insert(activity);
            });
        }
        if (view.get() != null) {
            view.get().showNewActivitySuccessMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Map<LocalDate, List<Activity>> parseActivities(List<Activity> activities, Set<LocalDate> datesSet) {
        Map<LocalDate, List<Activity>> activitiesOnEachDayMap = new HashMap<>();
        LocalDate from = trip.getFrom();
        LocalDate to = trip.getTo();
        long duration = ChronoUnit.DAYS.between(from, to) + 1;
        for (int i = 0; i < duration; i++) {
            datesSet.add(from.plusDays(i));
            activitiesOnEachDayMap.put(from.plusDays(i), new ArrayList<>());
        }
        for (Activity a : activities) {
            List<Activity> activitiesOfTheDay = activitiesOnEachDayMap.get(a.getDate());
            if (activitiesOfTheDay == null) {
                activitiesOfTheDay = new ArrayList<>();
            }
            activitiesOfTheDay.add(a);
        }
        return activitiesOnEachDayMap;
    }

}
