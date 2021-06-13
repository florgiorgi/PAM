package ar.edu.itba.pam.travelapp.model.weather.dtos.location;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("ID")
    private final String id;
    private final String localizedName;
    private final String englishName;

    public Country(String id, String localizedName, String englishName) {
        this.id = id;
        this.localizedName = localizedName;
        this.englishName = englishName;
    }

    public String getId() {
        return id;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getEnglishName() {
        return englishName;
    }
}
