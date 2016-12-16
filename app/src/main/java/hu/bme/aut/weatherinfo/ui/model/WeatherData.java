package hu.bme.aut.weatherinfo.ui.model;


import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class WeatherData extends SugarRecord{

    public List<Weather> weather;
    public MainWeatherData main;
    public Wind wind;
    public String cityname;
    public Long timeofdownload;

    public WeatherData(String cityname) {
        this.cityname = cityname;
    }

    public Long getTimeofdownload() {
        return timeofdownload;
    }

    public void setTimeofdownload(Long timeofdownload) {
        this.timeofdownload = timeofdownload;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public static boolean isExistsFor(String cityName) {
        List<WeatherData> wds = Select.from(WeatherData.class).where(Condition.prop("cityname").eq(cityName)).list();
        if (wds.isEmpty()) return false;
        else return true;
    }

    public static WeatherData findOrCreate(String cityName) {
        List<WeatherData> wds = Select.from(WeatherData.class).where(Condition.prop("cityname").eq(cityName)).list();
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
