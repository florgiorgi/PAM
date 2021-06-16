package ar.edu.itba.pam.travelapp.model.activity;

import java.util.List;

import io.reactivex.Flowable;

public interface ActivityRepository {

    Flowable<List<Activity>> getActivities();

    Flowable<List<Activity>> findByTripId(long tripId);

    Flowable<Activity> findById(long id);

    long insert(Activity activity);

    void delete(Activity activity);

    void update(Activity activity);

}
