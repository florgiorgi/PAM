package ar.edu.itba.pam.travelapp.newtrip.autocomplete.cities;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;

public class CityViewHolder extends RecyclerView.ViewHolder {
    public View view;
    private final Context context;
    private OnCityClickedListener listener;

    public CityViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.view = itemView;
        this.context = context;
    }

    public void setOnClickListener(OnCityClickedListener listener) {
        this.listener = listener;
    }

    public void bind(final City city) {
        final TextView title = itemView.findViewById(R.id.city_name);
        String cityName = city.getLocalizedName();
        String stateName = city.getAdministrativeArea().getLocalizedName();
        String countryName = city.getCountry().getLocalizedName();
        String titleText = context.getResources().getString(R.string.select_location_city,
                cityName, stateName, countryName);
        title.setText(titleText);
        view.setOnClickListener(v -> listener.onClick(city));
    }
}
