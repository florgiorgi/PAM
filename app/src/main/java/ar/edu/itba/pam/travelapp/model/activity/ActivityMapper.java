package ar.edu.itba.pam.travelapp.model.activity;

public class ActivityMapper {

    public Activity fromEntity(ActivityEntity entity) {
        return new Activity(entity.getName(), entity.getTripId(), entity.getDate());
    }

    public ActivityEntity toEntity(Activity activity) {
        return new ActivityEntity(activity.getName(), activity.getTripId(), activity.getDate());
    }


}
