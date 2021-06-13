package ar.edu.itba.pam.travelapp.model.weather.dtos.forecast;

public class Temperatures {
    private final Minimum minimum;
    private final Maximum maximum;

    public Temperatures(Minimum minimum, Maximum maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Minimum getMinimum() {
        return minimum;
    }

    public Maximum getMaximum() {
        return maximum;
    }
}
