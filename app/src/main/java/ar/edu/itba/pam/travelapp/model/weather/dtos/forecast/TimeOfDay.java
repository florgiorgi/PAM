package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

public abstract class TimeOfDay {
    private final Integer icon;
    private final String iconPhrase;
    private final Boolean hasPrecipitation;

    public TimeOfDay(Integer icon, String iconPhrase, Boolean hasPrecipitation) {
        this.icon = icon;
        this.iconPhrase = iconPhrase;
        this.hasPrecipitation = hasPrecipitation;
    }

    public Integer getIcon() {
        return icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public Boolean getHasPrecipitation() {
        return hasPrecipitation;
    }
}
