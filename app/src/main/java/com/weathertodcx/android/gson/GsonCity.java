package com.weathertodcx.android.gson;

/**
 * GsonCity结构
 * Created by Administrator on 2017/4/19.
 */

public class GsonCity extends GsonBase {

    private int id;
    private String name;

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
}
