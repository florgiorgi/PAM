package ar.edu.itba.pam.travelapp.newtrip.autocomplete;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.pam.travelapp.model.weather.WeatherRepository;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;
import ar.edu.itba.pam.travelapp.utils.SchedulerProvider;
import io.reactivex.disposables.Disposable;

public class AutocompletePresenter {
    private final WeatherRepository weatherRepository;
    private final SchedulerProvider schedulerProvider;

    private final WeakReference<AutocompleteView> view;
    private Disposable disposable;
    private List<City> cities;

    public AutocompletePresenter(AutocompleteView view, String city,
                                 WeatherRepository weatherRepository,
                                 SchedulerProvider schedulerProvider) {
        this.view = new WeakReference<>(view);
        this.weatherRepository = weatherRepository;
        this.schedulerProvider = schedulerProvider;
        this.cities = new ArrayList<>();
        getLocation(city);
    }

    private void getLocation(String city) {
        System.out.println("fetching city");
        this.disposable = weatherRepository.findCity(city)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onCityReceived, error -> {
                    if (view.get() != null) {
                        view.get().onCityError(error);
                    }
                });
    }

    private void onCityReceived(List<City> cities) {
        this.cities = cities;
        showCities();
    }

    private void showCities() {
        if (view.get() != null) {
            view.get().bind(this.cities);
        }
    }

    public void onViewDetached() {
        disposable.dispose();
    }
}
