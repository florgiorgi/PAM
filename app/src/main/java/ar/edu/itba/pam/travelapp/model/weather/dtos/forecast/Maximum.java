package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

import ar.edu.itba.pam.travelapp.model.weather.dtos.Temperature;

public class Maximum extends Temperature {
    protected Maximum(Integer value, String unit, Integer unitType) {
        super(value, unit, unitType);
    }
}
