package ar.edu.itba.pam.travelapp.model.repository;

public abstract class WeatherApiService {
    private String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
