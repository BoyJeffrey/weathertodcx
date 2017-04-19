package com.weathertodcx.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 和风天气:forecast子项
 * Created by Administrator on 2017/4/19.
 */

public class GsonHFForecast {

    public String date;

    @SerializedName("cond")
    public More more;

    @SerializedName("tmp")
    public Temperature temperature;

    public class More {
        @SerializedName("txt_d")
        public String info;
    }

    public class Temperature {
        public String max;
        public String min;
    }

}
