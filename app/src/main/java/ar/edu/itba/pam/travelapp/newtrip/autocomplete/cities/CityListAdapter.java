package ar.edu.itba.pam.travelapp.newtrip.autocomplete.cities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.weather.dtos.location.City;

public class CityListAdapter extends RecyclerView.Adapter<CityViewHolder> {
    private List<City> cities;

    private OnCityClickedListener listener;

    public CityListAdapter() {
        this.cities = new ArrayList<>();
    }

    public void setOnClickListener(OnCityClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
//        printArray(cities);
        holder.bind(cities.get(position));
        holder.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }

    public void update(List<City> cities) {
        this.cities.clear();
        if (cities != null) {
            this.cities.addAll(cities);
        }
        notifyDataSetChanged();
    }

    private void printArray(List<City> cities) {
        if (cities == null) {
            System.out.println("NULL");
        } else if (cities.size() == 0) {
            System.out.println("empty list of cities");
        } else {
            for (City c: cities) {
                System.out.println("next city is " + c.getLocalizedName() + ", " + c.getCountry().getLocalizedName());
            }
        }
        System.out.println("-------------------");
    }
}
