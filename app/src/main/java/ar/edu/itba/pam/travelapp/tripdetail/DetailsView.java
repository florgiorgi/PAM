package ar.edu.itba.pam.travelapp.tripdetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.itba.pam.travelapp.model.activity.Activity;

public interface DetailsView {

    void showActivitiesErrorMessage();

    void showNewActivitySuccessMessage();

    void showDeletedTripSuccessMessage();

    void openEditTripDialog();

    void openConfirmDeleteDialog();

    void bindDataset(Set<LocalDate> dates, Map<LocalDate, List<Activity>> activities);

}
