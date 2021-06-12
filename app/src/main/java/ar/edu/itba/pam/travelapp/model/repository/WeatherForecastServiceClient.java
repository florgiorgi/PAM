package ar.edu.itba.pam.travelapp.model.repository;

import ar.edu.itba.pam.travelapp.model.weather.forecast.Forecast;

public class WeatherForecastServiceClient {
    private final WeatherForecastService service;
    private final String apiVersion;
    private final String apiKey;

    public WeatherForecastServiceClient(WeatherForecastService service, String apiVersion, String apiKey) {
        this.service = service;
        this.apiVersion = apiVersion;
        this.apiKey = apiKey;
    }

    public Forecast getForecast(String locationKey) {
        return null;
    }
}
