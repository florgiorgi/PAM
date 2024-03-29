package ar.edu.itba.pam.travelapp.model.weather.repository;

import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.FieldNamingPolicy.UPPER_CAMEL_CASE;
import static retrofit2.converter.gson.GsonConverterFactory.create;

public class WeatherServiceGenerator {
    private static final String PROTOCOL = "https";
    private static final String DOMAIN = "dataservice.accuweather.com";
    private static final String API_VERSION = "v1";
    // you may try these different API KEYs if one returns there's no more requests available
//    private static final String API_KEY = "UOf5u1nrlCvQTMENbTMXjn1gnxm9NPOT";
    private static final String API_KEY = "owAXGCVhiwtl9MoIw2tBxeylmwELKTlg";
//    private static final String API_KEY = "7UpVBPqZOAarMocErsBzBC2VHFiy6j5h";

    public static WeatherLocationService getWeatherLocationService() {
        String baseUrl = PROTOCOL + "://" + DOMAIN + "/locations/" + API_VERSION + "/cities/";
        GsonConverterFactory gsonConverterFactory = create(
                new GsonBuilder()
                        .setFieldNamingPolicy(UPPER_CAMEL_CASE)
                        .create());

        return ServiceGenerator.createService(baseUrl, gsonConverterFactory,
                WeatherLocationService.class, API_KEY);
    }

    public static WeatherForecastService getWeatherForecastService() {
        String baseUrl = PROTOCOL + "://" + DOMAIN + "/forecasts/"+ API_VERSION + "/daily/";
        GsonConverterFactory gsonConverterFactory = create(
                new GsonBuilder()
                        .setFieldNamingPolicy(UPPER_CAMEL_CASE)
                        .create());

        return ServiceGenerator.createService(baseUrl, gsonConverterFactory,
                WeatherForecastService.class, API_KEY);
    }
}
