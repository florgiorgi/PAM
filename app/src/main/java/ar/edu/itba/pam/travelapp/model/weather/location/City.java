package ar.edu.itba.pam.travelapp.model.weather.location;

import java.util.List;

public class City {
    private final Integer version;
    private final String key;
    private final String type;
    private final Integer rank;
    private final String localizedName;
    private final String englishName;
    private final String primaryPostalCode;
    private final Region region;
    private final Country country;
    private final AdministrativeArea administrativeArea;
    private final TimeZone timeZone;
    private final GeoPosition geoPosition;
    private final Boolean isAlias;
    private final List<SupplementalAdminArea> supplementalAdminAreas;
    private final List<String> dataSets;

    public City(Integer version, String key, String type, Integer rank, String localizedName,
                String englishName, String primaryPostalCode, Region region, Country country,
                AdministrativeArea administrativeArea, TimeZone timeZone, GeoPosition geoPosition,
                Boolean isAlias, List<SupplementalAdminArea> supplementalAdminAreas,
                List<String> dataSets) {
        this.version = version;
        this.key = key;
        this.type = type;
        this.rank = rank;
        this.localizedName = localizedName;
        this.englishName = englishName;
        this.primaryPostalCode = primaryPostalCode;
        this.region = region;
        this.country = country;
        this.administrativeArea = administrativeArea;
        this.timeZone = timeZone;
        this.geoPosition = geoPosition;
        this.isAlias = isAlias;
        this.supplementalAdminAreas = supplementalAdminAreas;
        this.dataSets = dataSets;
    }

    public Integer getVersion() {
        return version;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public Integer getRank() {
        return rank;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getPrimaryPostalCode() {
        return primaryPostalCode;
    }

    public Region getRegion() {
        return region;
    }

    public Country getCountry() {
        return country;
    }

    public AdministrativeArea getAdministrativeArea() {
        return administrativeArea;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public Boolean getAlias() {
        return isAlias;
    }

    public List<SupplementalAdminArea> getSupplementalAdminAreas() {
        return supplementalAdminAreas;
    }

    public List<String> getDataSets() {
        return dataSets;
    }
}
