package ar.edu.itba.pam.travelapp.newtrip.autocomplete.cities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;

public class CityViewHolder extends RecyclerView.ViewHolder {
    public View view;

    private OnCityClickedListener listener;

    public CityViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setOnClickListener(OnCityClickedListener listener) {
        this.listener = listener;
    }

    public void bind(final City city) {
        final TextView title = itemView.findViewById(R.id.city_name);
        title.setText(city.getLocalizedName() + ", " + city.getCountry().getLocalizedName());
        view.setOnClickListener(v -> listener.onClick(city));
    }
}
