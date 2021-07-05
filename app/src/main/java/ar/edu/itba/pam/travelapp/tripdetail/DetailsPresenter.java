package ar.edu.itba.pam.travelapp.tripdetail;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.dtos.DayDto;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.trip.TripRepository;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.Forecast;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import io.reactivex.disposables.Disposable;

public class DetailsPresenter {
    public static final int MAX_AMOUNT_OF_FORECASTS = 5;

    private final ActivityRepository activityRepository;
    private final WeatherRepository weatherRepository;
    private final WeakReference<DetailsView> view;
    private final AndroidSchedulerProvider schedulerProvider;
    private final Trip trip;
    private final TripRepository tripRepository;
    private Disposable disposable;
    private Map<LocalDate, DayDto> tripDaysMap;

    public DetailsPresenter(final DetailsView view, final Trip trip, final ActivityRepository activityRepository,
                            final TripRepository tripRepository, final AndroidSchedulerProvider schedulerProvider,
                            final WeatherRepository weatherRepository) {
        this.activityRepository = activityRepository;
        this.tripRepository = tripRepository;
        this.view = new WeakReference<>(view);
        this.schedulerProvider = schedulerProvider;
        this.weatherRepository = weatherRepository;
        this.trip = trip;
    }

    public void onViewAttached() {
        fetchActivities();
    }

    private void fetchActivities() {
        this.disposable = activityRepository.findByTripId(this.trip.getId())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onActivitiesReceived, error -> {
                    if (view.get() != null) {
                        view.get().showActivitiesErrorMessage();
                    }
                });
    }

    private void onActivitiesReceived(List<Activity> activities) {
        if (tripDaysMap == null) {
            Set<LocalDate> datesSet = new LinkedHashSet<>();
            parseActivities(activities, datesSet);
        }
        updateDays();
        fetchWeatherForecasts();
    }

    private void fetchWeatherForecasts() {
        if (!trip.isLocationKeySet()) {
            fetchLocationKey();
        } else {
            int amountOfDays = tripDaysMap.keySet().size();
            String locationKey = trip.getLocationKey();
            if (amountOfDays == 1) {
                fetchWeatherForecastForCityOneDay(locationKey);
            } else {
                fetchWeatherForecastForCityFiveDays(locationKey);
            }
        }
    }

    private void fetchWeatherForecastForCityOneDay(String locationKey) {
        this.disposable = weatherRepository.getForecastForCityForOneDay(locationKey)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onForecastReceived, error -> {
                    if (view.get() != null) {
                        view.get().onForecastError(error);
                    }
                });
    }

    private void fetchWeatherForecastForCityFiveDays(String locationKey) {
        this.disposable = weatherRepository.getForecastForCityForFiveDays(locationKey)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onForecastReceived, error -> {
                    if (view.get() != null) {
                        view.get().onForecastError(error);
                    }
                });
    }

    private void fetchLocationKey() {
        this.disposable = weatherRepository.findFirstMatchCity(trip.getLocation())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onCityReceived, error -> {
                    if (view.get() != null) {
                        view.get().onCityError(error);
                    }
                });
    }

    private void onCityReceived(City city) {
        trip.setLocationKey(city.getKey());
        fetchWeatherForecasts();
    }

    private void onForecastReceived(ForecastResponse forecast) {
        int i = 0;
        List<Forecast> daysForecasts = forecast.getDailyForecasts();
        for (LocalDate day : tripDaysMap.keySet()) {
            if (!day.isBefore(LocalDate.now())) {
                if (i < MAX_AMOUNT_OF_FORECASTS) {
                    tripDaysMap.get(day).setDayForecast(daysForecasts.get(i));
                    i++;
                }
            }
        }
        updateDays();
    }

    private void updateDays() {
        if (view.get() != null) {
            view.get().bindDaysDataset(tripDaysMap.keySet(), tripDaysMap);
        }
    }

    public void onViewDetached() {
        disposable.dispose();
    }

    public void onActivityCreate(String name, LocalDate date) {
        if (name != null && name.length() > 0) {
            Activity activity = new Activity(name, this.trip.getId(), date);
            AsyncTask.execute(() -> {
                long newActivityId = this.activityRepository.insert(activity);
                activity.setId(newActivityId);
                tripDaysMap.get(date).addActivityToDay(activity);
            });
        }
        if (view.get() != null) {
            view.get().showNewActivitySuccessMessage();
        }
    }

    public void onDeleteTrip() {
        if (view.get() != null) {
            view.get().openConfirmDeleteDialog();
        }
    }

    public void onEditTrip() {
        if (view.get() != null) {
            view.get().startEditTripActivity();
        }
    }

    private void parseActivities(List<Activity> activities, Set<LocalDate> datesSet) {
        if (tripDaysMap == null) tripDaysMap = new HashMap<>();
        LocalDate from = trip.getFrom();
        LocalDate to = trip.getTo();
        long duration = ChronoUnit.DAYS.between(from, to) + 1;
        for (int i = 0; i < duration; i++) {
            datesSet.add(from.plusDays(i));
            if (!tripDaysMap.containsKey(from.plusDays(i))) {
                tripDaysMap.put(from.plusDays(i), new DayDto());
            }
        }
        for (Activity a : activities) {
            DayDto activitiesOfTheDay = tripDaysMap.get(a.getDate());
            if (activitiesOfTheDay == null) {
                activitiesOfTheDay = new DayDto();
            }
            activitiesOfTheDay.addActivityToDay(a);
        }
    }

    public void moveActivity(LocalDate from, long activityId, LocalDate to) {
        Optional<Activity> activity;
        DayDto fromDay = tripDaysMap.get(from);
        activity = fromDay.getDayActivities().stream().filter(activity1 -> activity1.getId() == activityId).findFirst();
        if (activity.isPresent()) {
            fromDay.deleteActivityFromDay(activity.get());
            tripDaysMap.get(to).addActivityToDay(activity.get());
            fetchActivities();
            return;
        }
        couldNotFindActivity(activityId);
    }

    private void couldNotFindActivity(long activityId) {
        if (view.get() != null) {
            view.get().showActivityNotFoundErrorMessage(activityId);
        }
    }

    public void onConfirmDeleteTrip() {
        AsyncTask.execute(() -> this.tripRepository.deleteTrip(trip));
        if (view.get() != null) {
            view.get().showDeletedTripSuccessMessage();
        }
    }

    public void onActivityDelete(Activity activity) {
        AsyncTask.execute(() -> this.activityRepository.delete(activity));
        tripDaysMap.get(activity.getDate()).deleteActivityFromDay(activity);
        fetchActivities();
    }

    public void onActivityEdit(Activity activity, String name) {
        activity.setName(name);
        AsyncTask.execute(() -> this.activityRepository.update(activity));
        tripDaysMap.get(activity.getDate()).editActivityNameFromDay(activity.getId(), name);
        fetchActivities();
    }
}
