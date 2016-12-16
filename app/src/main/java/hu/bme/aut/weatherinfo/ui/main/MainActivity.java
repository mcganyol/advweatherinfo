package hu.bme.aut.weatherinfo.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import java.util.List;

import hu.bme.aut.weatherinfo.R;
import hu.bme.aut.weatherinfo.ui.details.DetailsActivity;
import hu.bme.aut.weatherinfo.ui.model.City;

public class MainActivity extends AppCompatActivity implements AddCityDialogListener, OnCitySelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView;
    private CityAdapter adapter;

    public static final String KEY_PREF_SYNC_CONN = "alphabetic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFab();
        initSettingsButton();
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

    @Override
    public void onCitySelected(String city) {
        Intent showDetailsIntent = new Intent();
        showDetailsIntent.setClass(MainActivity.this, DetailsActivity.class);
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_CITY_NAME, city);
        startActivity(showDetailsIntent);
    }

    private void initSettingsButton() {
        FloatingActionButton settingsButton = (FloatingActionButton) findViewById(R.id.settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showSettingsActivityIntent = new Intent();
                showSettingsActivityIntent.setClass(MainActivity.this, WeatherSettingsActivity.class);
                startActivity(showSettingsActivityIntent);
            }
        });
    }

    @Override
    public void onRemoveSelected(int citynr) {
        final int toDelete = citynr;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(getString(R.string.deltext) + " " + adapter.getCityNameById(toDelete) + "?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.Yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.removeCity(toDelete);
                    }
                });

        builder1.setNegativeButton(
                R.string.No,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog deleteConfirmation = builder1.create();
        deleteConfirmation.show();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.MainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CityAdapter(this);
        List<City> citiesToShow = City.getAllCities(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("alphabetic",true));
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {  //TODO miert nem megy?
        Log.d("yay","yay");
        if (key.equals(KEY_PREF_SYNC_CONN)) {
            initRecyclerView();
        }

    }
}
