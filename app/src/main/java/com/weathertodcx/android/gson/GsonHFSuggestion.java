package com.weathertodcx.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 和风天气:suggestion子项
 * Created by Administrator on 2017/4/19.
 */

public class GsonHFSuggestion {
    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    @SerializedName("sport")
    public Sport sport;

    public class Comfort {
        @SerializedName("txt")
        public String info;
    }


    public class CarWash {
        @SerializedName("txt")
        public String info;
    }


    public class Sport {
        @SerializedName("txt")
        public String info;
    }
}
