package hu.bme.aut.weatherinfo.ui.model;


import com.orm.SugarRecord;

import java.util.List;


public class WeatherData extends SugarRecord{

    public List<Weather> weather;
    public MainWeatherData main;
    public Wind wind;
    private String cityName;

}
