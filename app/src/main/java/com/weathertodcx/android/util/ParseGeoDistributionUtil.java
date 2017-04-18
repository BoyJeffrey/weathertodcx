package com.weathertodcx.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weathertodcx.android.db.base.GeoDistributionData;

import java.util.List;

/**
 * 地理分布：省-市-区
 * 数据解析工具
 * Created by Administrator on 2017/4/19.
 */

public class ParseGeoDistributionUtil<T extends GeoDistributionData> {

    private static final String TAG = "JsonUtil";

    /**
     * 解析和处理服务器返回的省级数据
     * @param jsonData
     */
    public  boolean handleResponse(String jsonData) {
        if(TextUtils.isEmpty(jsonData)) {
            Gson gson = new Gson();
            Log.w(TAG, "handleResponse jsonData: " +  jsonData);
            List<T> list = gson.fromJson(jsonData, new TypeToken<List<T>>(){}.getType());
            if(list != null)
                for (GeoDistributionData p : list) {
                    LogUtil.d(TAG, "GroDistributionData Info Read:" + p.toString());
                    // 保存数据库信息
                    p.save();
                }
            return true;
        }
        return false;
    }
}
