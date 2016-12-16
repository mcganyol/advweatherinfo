package hu.bme.aut.weatherinfo.ui.details;

import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;

import hu.bme.aut.weatherinfo.R;
import hu.bme.aut.weatherinfo.ui.model.WeatherData;
import hu.bme.aut.weatherinfo.ui.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements WeatherDataHolder {

    private static final String TAG = "DetailsActivity";
    public static final String EXTRA_CITY_NAME = "extra.city_name";

    private WeatherData weatherData = null;
    private int frequencyTolerance;

    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        city = getIntent().getStringExtra(EXTRA_CITY_NAME);
        getSupportActionBar().setTitle(getString(R.string.weather, city));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadWeatherData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public WeatherData getWeatherData() {
        return weatherData;
    }

    private void loadWeatherData() {

        frequencyTolerance = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("frequency_list","60000"));
        Date now = new Date();
        Log.d("ajjjaj", now.getTime() );
        if (WeatherData.isExistsFor(city) && now.getTime()/1000 + frequencyTolerance >= WeatherData.findOrCreate(city).getTimeofdownload()) {
            displayWeatherData(weatherData.findOrCreate(city));
            Toast.makeText(DetailsActivity.this, getString(R.string.time_of_last_dl) + WeatherData.findOrCreate(city).getTimeofdownload(), Toast.LENGTH_SHORT).show(); //TODO it is not a data but unixtime needs to be converted to date format
        }
        else {
            NetworkManager.getInstance().getWeather(city).enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    Log.d(TAG, "onResponse: " + response.code());
                    if (response.isSuccessful()) {
                        Date now = new Date();
                        WeatherData actual = response.body();
                        displayWeatherData(actual);
                        actual.setCityname(city);
                        actual.setTimeofdownload(new Long(now.getTime()/1000)); //unix epoch in 2038...
                        actual.save();

                    } else {
                        Toast.makeText(DetailsActivity.this, getString(R.string.error, response.message()), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(DetailsActivity.this, R.string.error_in_network_req, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void displayWeatherData(WeatherData receivedWeatherData) {
        weatherData = receivedWeatherData;
        ViewPager mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        DetailsPagerAdapter detailsPagerAdapter = new DetailsPagerAdapter(getSupportFragmentManager(), this);
        mainViewPager.setAdapter(detailsPagerAdapter);
    }
}
