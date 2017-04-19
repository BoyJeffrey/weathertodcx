package com.weathertodcx.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 和风天气:basic子项
 * Created by Administrator on 2017/4/19.
 */

public class GsonHFBasic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
