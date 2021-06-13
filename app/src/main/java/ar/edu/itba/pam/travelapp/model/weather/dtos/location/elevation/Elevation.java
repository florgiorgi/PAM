package ar.edu.itba.pam.travelapp.model.weather.dtos.location.elevation;

public class Elevation {
    private final Metric metric;
    private final Imperial imperial;

    public Elevation(Metric metric, Imperial imperial) {
        this.metric = metric;
        this.imperial = imperial;
    }

    public Metric getMetric() {
        return metric;
    }

    public Imperial getImperial() {
        return imperial;
    }
}
