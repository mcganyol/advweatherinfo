package hu.bme.aut.weatherinfo.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.util.Collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.bme.aut.weatherinfo.R;
import hu.bme.aut.weatherinfo.ui.model.City;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private final List<City> cities;
    private OnCitySelectedListener listener;
    private Context kon;

    public CityAdapter(OnCitySelectedListener listener) {
        this.listener = listener;
        cities = new ArrayList<City>();
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        CityViewHolder viewHolder = new CityViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.position = position;
        holder.nameTextView.setText(cities.get(position).name);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void addCity(City newCity) {
        int i = 0;
        boolean isItNew = true;
        while (i < cities.size()) {
            if (newCity.name.equals(cities.get(i).name)) isItNew = false;
            i++;
        }
        if (isItNew) {
            cities.add(newCity);
            notifyItemInserted(cities.size() - 1);
        }
    }

    public void removeCity(int position) {
        City.destroyCity(cities.get(position).name);
        cities.remove(position);
        notifyItemRemoved(position);
        if (position < cities.size()) {
            notifyItemRangeChanged(position, cities.size() - position);
        }
    }

    public String getCityNameById(int position) {
        return cities.get(position).name;
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        int position;

        TextView nameTextView;
        Button removeButton;

        public CityViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.CityItemNameTextView);
            removeButton = (Button) itemView.findViewById(R.id.CityItemRemoveButton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onCitySelected(cities.get(position).name);
                    }
                }
            });
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onRemoveSelected(position);
                    }
                }
            });
        }
    }
}
