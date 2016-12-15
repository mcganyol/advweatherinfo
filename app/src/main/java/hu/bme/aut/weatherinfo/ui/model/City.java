package hu.bme.aut.weatherinfo.ui.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
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
        Log.d("name:" , name);
        //List<City> cities = City.find(City.class, "name = ?", "szar");  //ORM
        List<City> cities = Select.from(City.class).where(Condition.prop("name").eq(name)).list();
        City varos; // both
        //varos = new City(name); //not-ORM
        if (cities.isEmpty()) { //ORM
            varos = new City(name);  //ORM
            varos.save();  //ORM
        } //ORM
        else { //ORM
            varos = cities.get(0); //ORM
        } //ORM
        return varos;
    }

    public static List<City> getAllCities() {
        List<City> cities = City.listAll(City.class);
        Collections.sort(cities); //alphabetical order should be allowed-disabled in settings
        return cities;
    }

    public static void destroyCity(String name) {
        List<City> toDestroy = City.find(City.class, "name = ?", name);
        if (!toDestroy.isEmpty()) {
            toDestroy.get(0).delete();  // delete != destroy  but no clue how destroy is invoked in sugar... we should manually look up for its weather data to clean up the database
        }
    }

}
