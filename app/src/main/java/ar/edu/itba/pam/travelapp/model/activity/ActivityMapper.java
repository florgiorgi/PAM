package ar.edu.itba.pam.travelapp.model.activity;

import java.util.ArrayList;
import java.util.List;


public class ActivityMapper {

    public Activity toModel(ActivityEntity entity) {
        return new Activity(entity.getId(), entity.getName(), entity.getTripId(), entity.getDate());
    }

    public List<Activity> toModel(List<ActivityEntity> entities) {
        List<Activity> activities = new ArrayList<>();
        entities.forEach(e -> activities.add(toModel(e)));
        return activities;
    }

    public ActivityEntity toEntity(Activity activity) {
        return new ActivityEntity(activity.getId(), activity.getName(), activity.getTripId(), activity.getDate());
    }

    public List<ActivityEntity> toEntity(List<Activity> activities) {
        List<ActivityEntity> entities = new ArrayList<>();
        activities.forEach(a -> entities.add(toEntity(a)));
        return entities;
    }

}
