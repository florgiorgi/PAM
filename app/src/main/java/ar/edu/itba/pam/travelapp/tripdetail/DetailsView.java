package ar.edu.itba.pam.travelapp.tripdetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;

public interface DetailsView {

    void showActivitiesErrorMessage();

    void showNewActivitySuccessMessage();

    void bindDataset(Set<LocalDate> dates, Map<LocalDate, List<Activity>> activities);

    void bindForecastToDay(ForecastResponse forecast);

    void onForecastError();
}
