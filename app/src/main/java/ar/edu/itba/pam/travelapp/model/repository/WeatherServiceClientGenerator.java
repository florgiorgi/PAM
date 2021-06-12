package ar.edu.itba.pam.travelapp.model.repository;

import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.FieldNamingPolicy.UPPER_CAMEL_CASE;
import static retrofit2.converter.gson.GsonConverterFactory.create;

public class WeatherServiceClientGenerator {
    private static final String PROTOCOL = "http";
    private static final String DOMAIN = "dataservice.accuweather.com";
    private static final String API_VERSION = "v1";
    private static final String API_KEY = "owAXGCVhiwtl9MoIw2tBxeylmwELKTlg";

    public static WeatherLocationServiceClient getWeatherLocationServiceClient() {
        String baseUrl = PROTOCOL + "://" + DOMAIN + "/location/" + API_VERSION + "/cities/";
        GsonConverterFactory gsonConverterFactory = create(
                new GsonBuilder()
                        .setFieldNamingPolicy(UPPER_CAMEL_CASE)
                        .create());

        return new WeatherLocationServiceClient(ServiceGenerator.createService(
                baseUrl, gsonConverterFactory, WeatherLocationService.class, API_KEY));
    }

    public static WeatherForecastServiceClient getWeatherForecastServiceClient() {
        String baseUrl = PROTOCOL + "://" + DOMAIN + "/"; // todo
        GsonConverterFactory gsonConverterFactory = create(
                new GsonBuilder()
                        .setFieldNamingPolicy(UPPER_CAMEL_CASE)
                        .create());

        return new WeatherForecastServiceClient(ServiceGenerator.createService(
                baseUrl, gsonConverterFactory, WeatherForecastService.class, API_KEY), API_VERSION);
    }
}
