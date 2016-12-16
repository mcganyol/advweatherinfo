package hu.bme.aut.weatherinfo.ui.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Collections;
import java.util.List;


public class City extends SugarRecord implements Comparable<City>{

    @Expose
    public String name;

    public City() {
        super();
    }

    public City (String name) {
        super();
        this.name = name;
    }

    public int compareTo(City other) {
        return name.compareTo(other.name);
    }

    public static City findOrCreate(String name) {
        List<City> cities = Select.from(City.class).where(Condition.prop("name").eq(name)).list();
        City varos;
        if (cities.isEmpty()) {
            varos = new City(name);
            varos.save();
        }
        else {
            varos = cities.get(0);
        }
        return varos;
    }

    public static List<City> getAllCities(boolean ordered) {
        List<City> cities = City.listAll(City.class);
        if (ordered) {
            Collections.sort(cities);
        }
        return cities;
    }

    public static void destroyCity(String name) {
        List<City> toDestroy = City.find(City.class, "name = ?", name);
        if (!toDestroy.isEmpty()) {
            toDestroy.get(0).delete(); //destroy != delete by rest api conventions
        }
    }

}
