package ar.edu.itba.pam.travelapp.model.activity;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

public class ActivityMapper {

    public Activity toModel(ActivityEntity entity) {
        return new Activity(entity.getName(), entity.getTripId(), entity.getDate());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Activity> toModel(List<ActivityEntity> entities) {
        List<Activity> activities = new ArrayList<>();
        entities.forEach(e -> activities.add(toModel(e)));
        return activities;
    }

    public ActivityEntity toEntity(Activity activity) {
        return new ActivityEntity(activity.getName(), activity.getTripId(), activity.getDate());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<ActivityEntity> toEntity(List<Activity> activities) {
        List<ActivityEntity> entities = new ArrayList<>();
        activities.forEach(a -> entities.add(toEntity(a)));
        return entities;
    }

}
