package ar.edu.itba.pam.travelapp.model.activity;

import java.util.List;

import io.reactivex.Flowable;

public class ActivityRoomRepository implements ActivityRepository {

    private final ActivityDao dao;
    private final ActivityMapper mapper;

    private Flowable<List<Activity>> activities;

    public ActivityRoomRepository(ActivityDao dao, ActivityMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Flowable<List<Activity>> getActivities() {
        if (activities == null) {
            activities = dao.getActivities().map(mapper::toModel);
        }
        return activities;
    }

    @Override
    public Flowable<List<Activity>> findByTripId(long tripId) {
        return dao.findByTripId(tripId).map(mapper::toModel);
    }

    @Override
    public Flowable<Activity> findById(long id) {
        return dao.findById(id).map(mapper::toModel);
    }

    @Override
    public long insert(Activity activity) {
        this.activities = null;
        return dao.insert(mapper.toEntity(activity));
    }

    @Override
    public void update(Activity activity) {
        this.activities = null;
        dao.update(mapper.toEntity(activity));
    }

    @Override
    public void delete(Activity activity) {
        this.activities = null;
        dao.delete(mapper.toEntity(activity));
    }


}
