package hu.bme.aut.weatherinfo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


import java.util.List;

import hu.bme.aut.weatherinfo.R;
import hu.bme.aut.weatherinfo.ui.details.DetailsActivity;
import hu.bme.aut.weatherinfo.ui.model.City;

public class MainActivity extends AppCompatActivity implements AddCityDialogListener {

    private RecyclerView recyclerView;
    private CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFab();
        initRecyclerView();
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddCityDialogFragment().show(getSupportFragmentManager(), AddCityDialogFragment.TAG);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.MainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CityAdapter(new OnCitySelectedListener() {
            @Override
            public void onCitySelected(String city) {
                Intent showDetailsIntent = new Intent();
                showDetailsIntent.setClass(MainActivity.this, DetailsActivity.class);
                showDetailsIntent.putExtra(DetailsActivity.EXTRA_CITY_NAME, city);
                startActivity(showDetailsIntent);
            }

            @Override
            public void onRemoveSelected(int citynr) {
                adapter.removeCity(citynr);
            }
        });
        List<City> citiesToShow = City.getAllCities();
        int i = 0;
        while (i < citiesToShow.size()) {
            adapter.addCity(citiesToShow.get(i));
            i++;
        }



        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onCityAdded(City city) {
        adapter.addCity(city);
    }

}
