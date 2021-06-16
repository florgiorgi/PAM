package ar.edu.itba.pam.travelapp.tripdetail;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import ar.edu.itba.pam.travelapp.model.dtos.DayDto;

public interface DetailsView {
    void showActivitiesErrorMessage();

    void showNewActivitySuccessMessage();

    void bindDaysDataset(Set<LocalDate> dates, Map<LocalDate, DayDto> datesData);

    void onForecastError();

    void onCityError();

    void showDeletedTripSuccessMessage();

    void openConfirmDeleteDialog();

    void startEditTripActivity();
}
