package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

public class Night extends TimeOfDay {
    public Night(Integer icon, String iconPhrase, Boolean hasPrecipitation) {
        super(icon, iconPhrase, hasPrecipitation);
    }
}
