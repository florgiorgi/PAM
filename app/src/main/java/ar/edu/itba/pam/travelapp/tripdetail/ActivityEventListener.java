package ar.edu.itba.pam.travelapp.tripdetail;

import java.time.LocalDate;

import ar.edu.itba.pam.travelapp.model.activity.Activity;

public interface ActivityEventListener {
    void onClickNewActivity(String name, LocalDate date);

    void onDeleteActivity(Activity activity);

    void onEditActivity(Activity activity, String name);

    void onMoveActivity(LocalDate from, long activityId, LocalDate to);
}
