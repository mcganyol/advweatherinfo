package hu.bme.aut.weatherinfo.ui.model;


import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class City extends SugarRecord {


    private String name;

    public City (String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static City findOrCreate(String name) {
        List<City> cities = City.find(City.class, "name = ?", name);  //ORM
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
        List<City> cities = City.listAll(City.class);  //ORM
        //List<City> cities =  new ArrayList<City>();   //not-ORM
        //cities.add(new City("budaNEMORMpest")); //not-ORM
        return cities;
    }

    public static void destroyCity(String name) {
        List<City> toDestroy = City.find(City.class, "name = ?", name);
        if (!toDestroy.isEmpty()) {
            toDestroy.get(0).delete();  // delete != destroy  but no clue how destroy is invoked in sugar... we should manually look up for its weather data to clean up the database
        }
    }

}
