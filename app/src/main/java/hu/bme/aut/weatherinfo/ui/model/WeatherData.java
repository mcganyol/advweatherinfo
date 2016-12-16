package hu.bme.aut.weatherinfo.ui.model;


import android.preference.PreferenceManager;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class WeatherData extends SugarRecord{

    public List<Weather> weather;
    public MainWeatherData main;
    public Wind wind;
    public String cityName;
    public Long timeOfDownload;

    public WeatherData(String cityName) {
        this.cityName = cityName;
    }

    public Long getTimeOfDownload() {
        return timeOfDownload;
    }

    public void setTimeOfDownload(Long timeOfDownload) {
        this.timeOfDownload = timeOfDownload;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public static boolean isExistsFor(String cityName) {
        List<WeatherData> wds = Select.from(WeatherData.class).where(Condition.prop("cityName").eq(cityName)).list();
        if (wds.isEmpty()) return false;
        else return true;
    }

    public static WeatherData findOrCreate(String cityName) {
        List<WeatherData> wds = Select.from(WeatherData.class).where(Condition.prop("cityName").eq(cityName)).list();
        WeatherData wd;
        if (wds.isEmpty()) {
            wd = new WeatherData(cityName);
            wd.save();
        }
        else {
            wd = wds.get(0);
        }
        return wd;
    }

}
