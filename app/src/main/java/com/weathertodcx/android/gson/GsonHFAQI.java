package com.weathertodcx.android.gson;

/**
 * 和风天气:aqi子项
 * Created by Administrator on 2017/4/19.
 */

public class GsonHFAQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
