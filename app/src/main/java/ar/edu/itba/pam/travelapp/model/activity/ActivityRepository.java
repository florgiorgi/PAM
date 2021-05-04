package ar.edu.itba.pam.travelapp.model.activity;

import java.util.List;

import io.reactivex.Flowable;

public interface ActivityRepository {

    Flowable<List<Activity>> getActivities();

    Flowable<List<ActivityEntity>> findByTripId(long tripId);

    Flowable<ActivityEntity> findById(long id);

    void insert(Activity activity);

    void delete(Activity activity);

    void update(Activity activity);

}
