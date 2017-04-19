package com.weathertodcx.android.util;

import com.google.gson.Gson;
import com.weathertodcx.android.gson.GsonHFWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Gson方式解析json
 * 访问服务url:
 * https://free-api.heweather.com/v5/weather?city=CN101020500&key=34064739660c480e9527955864a2db7e
 * 所得返回结果样例:
 * {"HeWeather5":[{"aqi":{"city":{"aqi":"76","pm10":"101","pm25":"30","qlty":"良"}},"basic":{"city":"嘉定","cnty":"中国","id":"CN101020500","lat":"31.383524","lon":"121.250333","update":{"loc":"2017-04-19 22:51","utc":"2017-04-19 14:51"}},"daily_forecast":[{"astro":{"mr":"00:22","ms":"11:09","sr":"05:22","ss":"18:26"},"cond":{"code_d":"100","code_n":"300","txt_d":"晴","txt_n":"阵雨"},"date":"2017-04-19","hum":"54","pcpn":"1.7","pop":"86","pres":"1010","tmp":{"max":"26","min":"16"},"uv":"9","vis":"16","wind":{"deg":"146","dir":"东南风","sc":"微风","spd":"7"}},{"astro":{"mr":"01:07","ms":"12:03","sr":"05:21","ss":"18:27"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-04-20","hum":"67","pcpn":"1.1","pop":"100","pres":"1009","tmp":{"max":"23","min":"13"},"uv":"8","vis":"12","wind":{"deg":"284","dir":"北风","sc":"微风","spd":"1"}},{"astro":{"mr":"01:49","ms":"13:00","sr":"05:20","ss":"18:27"},"cond":{"code_d":"104","code_n":"104","txt_d":"阴","txt_n":"阴"},"date":"2017-04-21","hum":"60","pcpn":"0.0","pop":"0","pres":"1013","tmp":{"max":"22","min":"13"},"uv":"9","vis":"18","wind":{"deg":"293","dir":"西北风","sc":"微风","spd":"1"}}],"hourly_forecast":[],"now":{"cond":{"code":"104","txt":"阴"},"fl":"19","hum":"65","pcpn":"0","pres":"1009","tmp":"19","vis":"7","wind":{"deg":"140","dir":"东南风","sc":"4-5","spd":"29"}},"status":"ok","suggestion":{"air":{"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"},"comf":{"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},"flu":{"brf":"少发","txt":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。"},"sport":{"brf":"较不宜","txt":"天气较好，但风力较强，在户外要选择合适的运动，另外考虑到天气炎热，建议停止高强度运动。"},"trav":{"brf":"适宜","txt":"天气较好，温度适宜，但风稍微有点大。这样的天气适宜旅游，您可以尽情地享受大自然的无限风光。"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}}}]}
 */

public class GsonUtility {

    /**
     * 解析服务响应参数
     *
     * @param response
     * @return
     */
    public static GsonHFWeather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, GsonHFWeather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
