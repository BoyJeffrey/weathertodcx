package com.weathertodcx.android.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weathertodcx.android.gson.GsonBase;
import com.weathertodcx.android.gson.GsonCity;
import com.weathertodcx.android.gson.GsonCounty;
import com.weathertodcx.android.gson.GsonProvice;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 地理分布：省-市-区
 * 数据解析工具
 * Created by Administrator on 2017/4/19.
 */

public class ParseGeoDistributionUtil {

    private Class class_;

    private static final String TAG = "ParseGeoDistributionUtil";

    public ParseGeoDistributionUtil(Class class_) {
        this.class_ = class_;
    }

    /**
     * 解析和处理服务器返回的数据:省-市-县级
     *
     * @param jsonData 服务器端返回的json字符串
     * @param param       如果为省时，传送空;如果为城市时,传送对应省份ID做保存;如果为县时,则传送对应城市ID做保存;
     * @return list 解析出数据则返回, 否则返回空
     */
    public boolean handleResponse(String jsonData, int param) {
        boolean result = false;
        // 打印日志
        LogUtil.d(TAG, "handleResponse jsonData: " + jsonData);
        if (! TextUtils.isEmpty(jsonData)) {
            // json转换为db列表
            List<? extends DataSupport> dbList = jsonConverToDblist(jsonData, param);
            if (dbList.size() > 0) {
                // 先删除数据
                DataSupport.deleteAll(dbList.get(0).getClass());
                for (DataSupport p : dbList) {
                    LogUtil.d(TAG, "DataSupport Info Read:" + p.toString());
                    // 依次保存数据库信息
                    p.save();
                }
                result = true;
            }
        }
        return result;
    }

    /**
     * json转换为gson列表后,再由gson列表转换为数据库对象列表
     *
     * @param jsonData 服务器端返回的json字符串
     * @param param 返回Db对象时补充字段：地理位置为省级时,值无需处理;为市时,值为SQLLite数据库中省ID;值为县时,值为SQLLite数据库中市ID
     * @return Db对象列表
     */
    private List<? extends DataSupport> jsonConverToDblist(String jsonData, int param) {
        List<? extends GsonBase> list_ori = null;
        // 转换为Gson列表
        Gson gson = new Gson();
        if(class_ ==  GsonProvice.class)
            list_ori = gson.fromJson(jsonData, new TypeToken<List<GsonProvice>>() {}.getType());
        else if(class_ == GsonCity.class)
            list_ori = gson.fromJson(jsonData, new TypeToken<List<GsonCity>>() {}.getType());
        else if(class_ == GsonCounty.class)
            list_ori = gson.fromJson(jsonData, new TypeToken<List<GsonCounty>>() {}.getType());
        // gson列表转换为Db列表
        return new GsonBase().convertToDbList(list_ori, param);
    }

}
