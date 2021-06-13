package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {
    private final String date;
    private final Integer epochDate;
    @SerializedName("Temperature")
    private final Temperatures temperature;
    private final Day day;
    private final Night night;
    private final List<String> sources;
    private final String mobileLink;
    private final String link;

    public Forecast(String date, Integer epochDate, Temperatures temperature, Day day, Night night,
                    List<String> sources, String mobileLink, String link) {
        this.date = date;
        this.epochDate = epochDate;
        this.temperature = temperature;
        this.day = day;
        this.night = night;
        this.sources = sources;
        this.mobileLink = mobileLink;
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public Integer getEpochDate() {
        return epochDate;
    }

    public Temperatures getTemperature() {
        return temperature;
    }

    public Day getDay() {
        return day;
    }

    public Night getNight() {
        return night;
    }

    public List<String> getSources() {
        return sources;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public String getLink() {
        return link;
    }
}
