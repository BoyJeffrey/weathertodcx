package com.weathertodcx.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 和风天气:服务器api反参一览:
 * {
 *     "HeWeather": [
     *     {
     *         "status":"ok",
     *         "basic":{},
     *         "aqi":{},
     *         "now":{},
     *         "suggestion":{},
     *         "daily_forecast":[]
     *     }
 *     ]
 * }
 * Created by Administrator on 2017/4/19.
 */

public class GsonHFWeather {

    public String status;

    @SerializedName("basic")
    public GsonHFBasic basic;

    @SerializedName("aqi")
    public GsonHFAQI aqi;

    @SerializedName("now")
    public GsonHFNow now;

    @SerializedName("suggestion")
    public GsonHFSuggestion suggestion;

    @SerializedName("daily_forecast")
    public List<GsonHFForecast> forecastList;
}
