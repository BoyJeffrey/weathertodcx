package com.weathertodcx.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 和风天气:now子项
 * Created by Administrator on 2017/4/19.
 */

public class GsonHFNow {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}
