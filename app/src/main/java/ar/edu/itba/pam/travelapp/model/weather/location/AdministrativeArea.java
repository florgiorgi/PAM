package ar.edu.itba.pam.travelapp.model.weather.location;

import com.google.gson.annotations.SerializedName;

public class AdministrativeArea {
    @SerializedName("ID")
    private final String id;
    private final String localizedName;
    private final String englishName;
    private final Integer level;
    private final String localizedType;
    private final String englishType;
    private final String countryID;

    public AdministrativeArea(String id, String localizedName, String englishName, Integer level,
                              String localizedType, String englishType, String countryID) {
        this.id = id;
        this.localizedName = localizedName;
        this.englishName = englishName;
        this.level = level;
        this.localizedType = localizedType;
        this.englishType = englishType;
        this.countryID = countryID;
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

    public Integer getLevel() {
        return level;
    }

    public String getLocalizedType() {
        return localizedType;
    }

    public String getEnglishType() {
        return englishType;
    }

    public String getCountryID() {
        return countryID;
    }
}
