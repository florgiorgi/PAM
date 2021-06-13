package ar.edu.itba.pam.travelapp.model.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.pam.travelapp.model.weather.location.City;
import ar.edu.itba.pam.travelapp.utils.networking.NotFoundException;
import ar.edu.itba.pam.travelapp.utils.networking.RetrofitUtils;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class WeatherLocationServiceClient {
//    private final WeatherLocationService service;
//    private final String apiKey;
//
//    public WeatherLocationServiceClient(WeatherLocationService service, String apiKey) {
//        this.service = service;
//        this.apiKey = apiKey;
//    }
//
//    public Flowable<List<City>> findCity(String city) {
//        return RetrofitUtils.performRequest(service.findCity(apiKey, city)).map(mapper::mapToLocation);
////        try {
////            return service.findCity(apiKey, city).execute().body();
////        } catch (IOException e) {
////            throw RetrofitUtils.convertException(e);
////        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public Single<City> findFirstCity(String city) throws Throwable {
//        List<City> possibleCities;
////        return RetrofitUtils.performRequest(service.findCity())
////        try {
////            possibleCities = service.findCity(apiKey, city).execute().body();
////            if (possibleCities == null) {
////                throw new NotFoundException("City not found");
////            }
////        } catch (IOException e) {
////            throw RetrofitUtils.convertException(e);
////        }
////        if (!possibleCities.isEmpty()) {
////            return Optional.of(possibleCities.get(0));
////        }
////        return Optional.empty();
//    }
//
//    public String getKeyForCity(City city) {
//        return city.getKey();
//    }
}
