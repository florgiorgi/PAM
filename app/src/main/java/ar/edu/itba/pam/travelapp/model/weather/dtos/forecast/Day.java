package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

public class Day extends TimeOfDay {
    public Day(Integer icon, String iconPhrase, Boolean hasPrecipitation) {
        super(icon, iconPhrase, hasPrecipitation);
    }
}
