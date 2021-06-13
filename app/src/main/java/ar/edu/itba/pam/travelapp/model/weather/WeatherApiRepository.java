package ar.edu.itba.pam.travelapp.model.weather;

import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.ForecastResponse;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherForecastService;
import ar.edu.itba.pam.travelapp.model.weather.repository.WeatherLocationService;
import ar.edu.itba.pam.travelapp.utils.networking.RetrofitUtils;
import io.reactivex.Single;

public class WeatherApiRepository implements WeatherRepository {
    private final WeatherLocationService weatherLocationService;
    private final WeatherForecastService weatherForecastService;

    public WeatherApiRepository(WeatherLocationService weatherLocationService,
                                WeatherForecastService weatherForecastService) {
        this.weatherLocationService = weatherLocationService;
        this.weatherForecastService = weatherForecastService;
    }

    @Override
    public Single<ForecastResponse> getForecastForCity(String cityKey) {
        return RetrofitUtils.performRequest(weatherForecastService.getCurrentForecast(cityKey, true));
    }

    // todo
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    public Single<City> findCity(String city) throws Throwable {
//        Flowable<City> cities = weatherLocationService.findCity(city);
//        for (City c: cities) {
//            if (c != null) {
//                if (city.equals(c.getEnglishName()) || city.equals(c.getLocalizedName())) {
//                    return Optional.of(c);
//                }
//            }
//        }
//        return Optional.empty();
//    }

//    @Override
//    public Single<Forecast> getForecastForCity(City city) {
//        return weatherForecastService.getCurrentForecast(city.getKey());
//    }
}
