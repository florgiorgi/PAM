package ar.edu.itba.pam.travelapp.newtrip.autocomplete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.di.newtrip.autocomplete.AutocompleteContainer;
import ar.edu.itba.pam.travelapp.di.newtrip.autocomplete.AutocompleteContainerLocator;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;
import ar.edu.itba.pam.travelapp.newtrip.autocomplete.cities.CitiesView;
import ar.edu.itba.pam.travelapp.newtrip.autocomplete.cities.CityListAdapter;
import ar.edu.itba.pam.travelapp.newtrip.autocomplete.cities.OnCityClickedListener;
import ar.edu.itba.pam.travelapp.utils.AndroidSchedulerProvider;

public class AutocompleteActivity extends AppCompatActivity implements AutocompleteView, OnCityClickedListener {
    private TextView cityShown;
    private String cityName;
    private List<City> cities;
    private Button confirmBtn;

    private RecyclerView citiesRecyclerView;
    private CityListAdapter adapter;

    private CitiesView citiesView;

    private AutocompletePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete);
        this.cityName = (String) getIntent().getStringExtra("city");
        createPresenter();
        initView();
    }

    private void createPresenter() {
        Object possibleAutocompletePresenter = getLastNonConfigurationInstance();
        if (possibleAutocompletePresenter instanceof AutocompletePresenter) {
            this.presenter = (AutocompletePresenter) possibleAutocompletePresenter;
        }
        if (this.presenter == null) {
            AutocompleteContainer container = AutocompleteContainerLocator.locateComponent(this);
            this.presenter = new AutocompletePresenter(this, cityName, container.getWeatherRepository(),
                    (AndroidSchedulerProvider) container.getSchedulerProvider());
        }
    }

    private void initView() {
        this.cityShown = findViewById(R.id.city_name);
        this.cityShown.setText(this.cityName);
        this.confirmBtn = findViewById(R.id.confirm_btn);
        this.confirmBtn.setOnClickListener(view -> onClickConfirm());
        this.citiesRecyclerView = findViewById(R.id.cities_list);
        this.citiesRecyclerView.setHasFixedSize(true);
        this.adapter = new CityListAdapter();
        this.adapter.setOnClickListener(this);
        this.citiesRecyclerView.setAdapter(adapter);
        this.citiesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void bind(List<City> cities) {
        adapter.update(cities);
    }

    @Override
    public void onCityError(Throwable error) {
        String errorMessage = error.getMessage();
        if (errorMessage == null) {
            Toast.makeText(AutocompleteActivity.this, this.getResources().getString(R.string.weather_no_city), Toast.LENGTH_SHORT).show();
        } else if (errorMessage.contains("503")) {
            Toast.makeText(AutocompleteActivity.this, this.getResources().getString(R.string.weather_high_traffic), Toast.LENGTH_SHORT).show();
        } else if (errorMessage.contains("401")) {
            Toast.makeText(AutocompleteActivity.this, this.getResources().getString(R.string.weather_free_trial), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AutocompleteActivity.this, this.getResources().getString(R.string.weather_no_city), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(City city) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("cityKey", city.getKey());
        putCityShownAndSetResult(Activity.RESULT_OK, returnIntent);
    }

    @Override
    public void onClickConfirm() {
        putCityShownAndSetResult(Activity.RESULT_CANCELED, new Intent());
    }

    private void putCityShownAndSetResult(int activityStatus, @NonNull Intent intent) {
        intent.putExtra("city", cityShown.getText().toString());
        setResult(activityStatus, intent);
        finish();
    }
}
