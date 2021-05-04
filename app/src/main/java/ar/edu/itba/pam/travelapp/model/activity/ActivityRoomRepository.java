package ar.edu.itba.pam.travelapp.model.activity;

import java.util.List;

import io.reactivex.Flowable;

public class ActivityRoomRepository implements ActivityRepository {

    private ActivityDao dao;
    private ActivityMapper mapper;

    public ActivityRoomRepository(ActivityDao dao, ActivityMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Flowable<List<Activity>> getActivities() {
        return null;
    }

    @Override
    public Flowable<List<ActivityEntity>> findByTripId(long tripId) {
        return null;
    }

    @Override
    public Flowable<ActivityEntity> findById(long id) {
        return null;
    }

    @Override
    public void insert(Activity activity) {

    }

    @Override
    public void delete(Activity activity) {

    }

    @Override
    public void update(Activity activity) {

    }
}
