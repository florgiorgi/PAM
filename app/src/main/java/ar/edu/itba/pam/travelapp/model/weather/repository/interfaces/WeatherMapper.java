package ar.edu.itba.pam.travelapp.model.weather.repository.interfaces;

import ar.edu.itba.pam.travelapp.model.weather.dtos.WeatherResponseModel;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherResponse;

public interface WeatherMapper {
    <T extends WeatherResponseModel> T map(WeatherResponse response);
}
