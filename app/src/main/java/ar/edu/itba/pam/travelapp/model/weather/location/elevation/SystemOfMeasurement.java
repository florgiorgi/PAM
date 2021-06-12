package ar.edu.itba.pam.travelapp.model.weather.location.elevation;

public abstract class SystemOfMeasurement {
    private final Integer value;
    private final String unit;
    private final Integer unitType;

    protected SystemOfMeasurement(Integer value, String unit, Integer unitType) {
        this.value = value;
        this.unit = unit;
        this.unitType = unitType;
    }

    public Integer getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getUnitType() {
        return unitType;
    }
}
