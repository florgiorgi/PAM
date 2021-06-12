package ar.edu.itba.pam.travelapp.model.weather.location;

import ar.edu.itba.pam.travelapp.model.weather.location.elevation.Elevation;

public class GeoPosition {
    private final Double latitude;
    private final Double longitude;
    private final Elevation elevation;

    public GeoPosition(Double latitude, Double longitude, Elevation elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Elevation getElevation() {
        return elevation;
    }
}
