package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

import ar.edu.itba.pam.travelapp.model.weather.dtos.Temperature;

public class Minimum extends Temperature {
    protected Minimum(Integer value, String unit, Integer unitType) {
        super(value, unit, unitType);
    }
}
