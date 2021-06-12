package ar.edu.itba.pam.travelapp.model.weather.location;

import com.google.gson.annotations.SerializedName;

public class Region {
    @SerializedName("ID")
    private final String id;
    private final String localizedName;
    private final String englishName;

    public Region(String id, String localizedName, String englishName) {
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
