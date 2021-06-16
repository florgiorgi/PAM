package ar.edu.itba.pam.travelapp.tripdetail;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.dtos.DayDto;
import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.Forecast;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import io.reactivex.disposables.Disposable;

public class DetailsPresenter {

    private final ActivityRepository activityRepository;
    private final WeatherRepository weatherRepository;
    private final WeakReference<DetailsView> view;
    private final AndroidSchedulerProvider schedulerProvider;
    private final Trip trip;
    private Disposable disposable;
    private Map<LocalDate, DayDto> tripDaysMap;

    public DetailsPresenter(final DetailsView view, final Trip trip, final ActivityRepository activityRepository,
                            final AndroidSchedulerProvider schedulerProvider,
                            final WeatherRepository weatherRepository) {
        this.activityRepository = activityRepository;
        this.view = new WeakReference<>(view);
        this.schedulerProvider = schedulerProvider;
        this.weatherRepository = weatherRepository;
        this.trip = trip;
    }

    public void onViewAttached() {
        fetchTrip();
        fetchWeatherForecasts();
    }

    private void fetchTrip() {
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
//        tripDaysMap = parseActivities(activities, datesSet);
            parseActivities(activities, datesSet);
        }
        updateDays();
    }

    private void fetchWeatherForecasts() {
        if (!trip.isLocationKeySet()) {
//            trip.setLocationKey("7894");
            fetchLocationKey();
        } else {
            int amountOfDays = tripDaysMap.keySet().size();
            String locationKey = trip.getLocationKey();
            if (amountOfDays == 1) {
                fetchWeatherForecastForCityOneDay(locationKey);
            } else {
                int amountOfWeatherFetches = (amountOfDays / 6) + 1;
                for (int i = 0; i <= amountOfWeatherFetches; i++) {
                    System.out.println("----one more----");
                    fetchWeatherForecastForCityFiveDays(locationKey);
                }
            }
        }
    }

    private void fetchWeatherForecastForCityOneDay(String locationKey) {
        this.disposable = weatherRepository.getForecastForCityForOneDay(locationKey)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onForecastReceived, error -> {
                    if (view.get() != null) {
                        view.get().onForecastError();
                    }
                });
    }

    private void fetchWeatherForecastForCityFiveDays(String locationKey) {
        this.disposable = weatherRepository.getForecastForCityForFiveDays(locationKey)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onForecastReceived, error -> {
                    if (view.get() != null) {
                        view.get().onForecastError();
                    }
                });
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
        fetchWeatherForecasts();
    }

    private void onForecastReceived(ForecastResponse forecast) {
        int i = 0;
        List<Forecast> daysForecasts = forecast.getDailyForecasts();
        for (LocalDate day: tripDaysMap.keySet()) {
            tripDaysMap.get(day).setDayForecast(daysForecasts.get(i));
            i++;
        }
        updateDays();
    }

    private void updateDays() {
        if (view.get() != null) {
            view.get().bindDaysDataset(tripDaysMap.keySet(), tripDaysMap);  // todo: move parse out of here and add forecasts to map
        }
    }

    public void onViewDetached() {
        disposable.dispose();
    }

    public void onActivityCreate(String name, LocalDate date) {
        if (name != null && name.length() > 0) {
            Activity activity = new Activity(name, this.trip.getId(), date);
            AsyncTask.execute(() -> this.activityRepository.insert(activity));
            tripDaysMap.get(date).addActivityToDay(activity);
        }
        if (view.get() != null) {
            view.get().showNewActivitySuccessMessage();
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
}
