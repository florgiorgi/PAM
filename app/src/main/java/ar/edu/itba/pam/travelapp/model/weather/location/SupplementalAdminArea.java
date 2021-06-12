package ar.edu.itba.pam.travelapp.model.weather.location;

public class SupplementalAdminArea {
    private final Integer level;
    private final String localizedName;
    private final String englishName;

    public SupplementalAdminArea(Integer level, String localizedName, String englishName) {
        this.level = level;
        this.localizedName = localizedName;
        this.englishName = englishName;
    }

    public Integer getLevel() {
        return level;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getEnglishName() {
        return englishName;
    }
}
