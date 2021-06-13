package ar.edu.itba.pam.travelapp.model.weather.dtos.location.elevation;

import ar.edu.itba.pam.travelapp.model.weather.dtos.Temperature;

public class Metric extends Temperature {
    protected Metric(Double value, String unit, Integer unitType) {
        super(value, unit, unitType);
    }
}
