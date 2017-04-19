package com.weathertodcx.android.gson;

import com.weathertodcx.android.db.City;
import com.weathertodcx.android.db.County;
import com.weathertodcx.android.db.Province;
import com.weathertodcx.android.util.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Gson基类
 * Created by Administrator on 2017/4/19.
 */

public class GsonBase {

    /**
     * 是否非空判断
     * @param list list
     * @return  list不空则true;反之亦然
     */
    private boolean listNotEmpty(List<?> list) {
        return !(list == null || list.size() == 0);
    }

    /**
     * gson对象转换为Db库中对象列表
     *
     * @param list gsonBase列表
     * @return dataSupport列表
     */
    public List<? extends DataSupport> convertToDbList(List<? extends GsonBase> list, int param) {
        if(listNotEmpty(list)) {
            // 检查当前gson对象类型
            Object checkObject = list.get(0);
            LogUtil.d("checkObject.getClass():" , checkObject.getClass().toString());
            if(checkObject instanceof GsonProvice) { // GsonProvice转换为Provice
                List<Province> dbList = new ArrayList<>();
                if(listNotEmpty(list)) {
                    for (Object o : list) {
                        GsonProvice gson = (GsonProvice) o;
                        // 转换为DB
                        Province db = new Province();
                        db.setProviceCode(gson.getId());
                        db.setProviceName(gson.getName());
                        dbList.add(db);
                    }
                }
                return dbList;
            }else if(checkObject instanceof GsonCity) { // GsonCity转换为City
                List<City> dbList = new ArrayList<>();
                if(listNotEmpty(list)) {
                    for (Object o : list) {
                        GsonCity gson = (GsonCity) o;
                        // 转换为DB
                        City db = new City();
                        db.setCityCode(gson.getId());
                        db.setCityName(gson.getName());
                        db.setProvinceId(param);
                        dbList.add(db);
                    }
                }
                return dbList;
            }else if (checkObject instanceof GsonCounty) { // 转换为County
                List<County> dbList = new ArrayList<>();
                if(listNotEmpty(list)) {
                    for (Object o : list) {
                        GsonCounty gson = (GsonCounty) o;
                        // 转换为DB
                        County db = new County();
                        db.setCountyName(gson.getName());
                        db.setWeatherId(gson.getWeather_id());
                        db.setCityId(param);
                        dbList.add(db);
                    }
                }
                return dbList;
            }
        }
        return new ArrayList<>();
    }

    /**
     * gson对象转换为Db库中对象
     *
     * @param o gsonBase对象
     * @return 预计返回dataSupport对象
     */
    Object convertToDbObject(GsonBase o, int param) {
        return null;
    }
}
