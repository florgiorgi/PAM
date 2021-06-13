package ar.edu.itba.pam.travelapp.model.weather.location.elevation;

import ar.edu.itba.pam.travelapp.model.weather.Temperature;

public class Imperial extends Temperature {
    protected Imperial(Integer value, String unit, Integer unitType) {
        super(value, unit, unitType);
    }
}
