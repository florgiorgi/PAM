package ar.edu.itba.pam.travelapp.newtrip.autocomplete.cities;

import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;

public interface OnCityClickedListener {
    void onClick(City city);
}
