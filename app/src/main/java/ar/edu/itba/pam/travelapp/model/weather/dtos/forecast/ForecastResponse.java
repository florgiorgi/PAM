package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.dtos.WeatherResponseModel;

public class ForecastResponse extends WeatherResponseModel {
    private final Headline headline;
    @SerializedName("DailyForecasts")
    private final List<Forecast> dailyForecasts;

    public ForecastResponse(Headline headline, List<Forecast> dailyForecasts) {
        this.headline = headline;
        this.dailyForecasts = dailyForecasts;
    }

    public Headline getHeadline() {
        return headline;
    }

    public List<Forecast> getDailyForecasts() {
        return dailyForecasts;
    }
}
