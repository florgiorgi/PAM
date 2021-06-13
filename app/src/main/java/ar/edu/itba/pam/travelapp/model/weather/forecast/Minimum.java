package ar.edu.itba.pam.travelapp.model.weather.forecast;

import ar.edu.itba.pam.travelapp.model.weather.Temperature;

public class Minimum extends Temperature {
    protected Minimum(Integer value, String unit, Integer unitType) {
        super(value, unit, unitType);
    }
}
