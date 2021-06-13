package ar.edu.itba.pam.travelapp.model.weather.repository;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import ar.edu.itba.pam.travelapp.model.weather.repository.interfaces.WeatherApiService;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static <T extends WeatherApiService> T createService(String baseUrl,
                GsonConverterFactory gsonConverterFactory, Class<T> serviceClass, String apiKey) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(sendRequestStep -> {
            Request originalRequest = sendRequestStep.request();
            HttpUrl originalUrl = originalRequest.url();

            HttpUrl newUrl = originalUrl.newBuilder()
                    .addQueryParameter("apikey", apiKey).build();

            Request newRequest = originalRequest.newBuilder().url(newUrl).build();
            return sendRequestStep.proceed(newRequest);
        });

        return new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(serviceClass);
    }
}
