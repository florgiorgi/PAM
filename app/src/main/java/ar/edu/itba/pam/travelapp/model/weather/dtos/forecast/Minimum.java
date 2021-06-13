package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

import ar.edu.itba.pam.travelapp.model.weather.dtos.Temperature;

public class Minimum extends Temperature {
    protected Minimum(Double value, String unit, Integer unitType) {
        super(value, unit, unitType);
    }
}
