package ar.edu.itba.pam.travelapp.model.dtos;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.Forecast;

public class DayDto {
    private final List<Activity> dayActivities;

    private Forecast dayForecast;

    public DayDto() {
        this.dayActivities = new ArrayList<>();
    }

    public DayDto(List<Activity> dayActivities) {
        this.dayActivities = dayActivities;
    }

    public void setDayForecast(Forecast dayForecast) {
        this.dayForecast = dayForecast;
    }

    public Forecast getDayForecast() {
        return dayForecast;
    }

    public List<Activity> getDayActivities() {
        return dayActivities;
    }

    public void addActivityToDay(Activity activity) {
        dayActivities.add(activity);
    }

    public void editActivityNameFromDay(long activityId, String newName) {
        for (Activity a: dayActivities) {
            if (a.getId() == activityId) {
                a.setName(newName);
                return;
            }
        }
    }

    public void deleteActivityFromDay(Activity activity) {
        for (Activity a: dayActivities) {
            if (a.getId() == activity.getId()) {
                dayActivities.remove(a);
                return;
            }
        }
    }
}
