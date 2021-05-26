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
import java.util.Set;

import androidx.annotation.RequiresApi;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.activity.ActivityRepository;
import ar.edu.itba.pam.travelapp.model.trip.Trip;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;
import io.reactivex.disposables.Disposable;

public class DetailsPresenter {

    private final ActivityRepository activityRepository;
    private final WeakReference<DetailsView> view;
    private final AndroidSchedulerProvider schedulerProvider;
    private final Trip trip;
    private Disposable disposable;

    public DetailsPresenter(ActivityRepository activityRepository, Trip trip, DetailsView view) {
        this.activityRepository = activityRepository;
        this.view = new WeakReference<>(view);
        this.schedulerProvider = new AndroidSchedulerProvider();
        this.trip = trip;
    }

    public void onViewAttached() {
        this.disposable = activityRepository.findByTripId(this.trip.getId())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onActivitiesReceived, error -> {
                    if (view.get() != null) {
                        view.get().showActivitiesErrorMessage();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onActivitiesReceived(List<Activity> activities) {
        System.out.println("received activities: " + activities);
        Set<LocalDate> datesSet = new LinkedHashSet<>();
        Map<LocalDate, List<Activity>> map = parseActivities(activities, datesSet);
        if (view.get() != null) {
            view.get().bindDataset(datesSet, map);
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
        onViewAttached();
        if (view.get() != null) {
            view.get().showNewActivitySuccessMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Map<LocalDate, List<Activity>> parseActivities(List<Activity> activities, Set<LocalDate> datesSet) {
        Map<LocalDate, List<Activity>> activitiesMap = new HashMap<>();
        LocalDate from = trip.getFrom();
        LocalDate to = trip.getTo();
        long duration = ChronoUnit.DAYS.between(from, to);
        for (int i = 0; i < duration + 2; i++) {
            activitiesMap.put(from.plusDays(i - 1), new ArrayList<Activity>());
            datesSet.add(from.plusDays(i - 1));
        }
        for (Activity a : activities) {
            activitiesMap.get(a.getDate()).add(a);
        }
        return activitiesMap;
    }

}