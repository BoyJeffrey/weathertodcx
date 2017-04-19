package com.weathertodcx.android.gson;

/**
 * GsonCounty结构
 * Created by Administrator on 2017/4/19.
 */

public class GsonCounty extends GsonBase {

    private int id;
    private String name;
    private String weather_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }
}
