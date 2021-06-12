package ar.edu.itba.pam.travelapp.model.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static <T extends WeatherApiService> WeatherApiService createService(String baseUrl,
            GsonConverterFactory gsonConverterFactory, Class<T> serviceClass, String apiKey) {

        WeatherApiService service = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(serviceClass);
        service.setApiKey(apiKey);
        return service;
    }
}
