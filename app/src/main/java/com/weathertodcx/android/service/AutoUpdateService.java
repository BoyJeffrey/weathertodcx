package com.weathertodcx.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.weathertodcx.android.gson.GsonHFWeather;
import com.weathertodcx.android.util.GsonUtility;
import com.weathertodcx.android.util.HttpUtil;
import com.weathertodcx.android.util.LogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.weathertodcx.android.WeatherActivity.HFSERVER_KEYWORD_CITY;
import static com.weathertodcx.android.WeatherActivity.HFSERVER_KEYWORD_KEY;
import static com.weathertodcx.android.WeatherActivity.HFServerKey;
import static com.weathertodcx.android.WeatherActivity.requestHFServerUrl;

public class AutoUpdateService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 定时更新SharePreferences中缓存数据
        updateWeather();
        // 定时更新Bing图片
        updateBingPic();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 * 1000; // 8小时
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气情况缓存
     */
    private void updateWeather() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather", null);
        if (weatherString != null) {
            // 有缓存直接解析天气数据并更新缓存
            GsonHFWeather weather = GsonUtility.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;
            String weatherUrl = requestHFServerUrl + "?" + HFSERVER_KEYWORD_CITY + "=" + weatherId + "&" + HFSERVER_KEYWORD_KEY + "=" + HFServerKey;
            Log.d(TAG, "requestWeather: " + weatherUrl);
            HttpUtil.sendOkHttpsRequest(weatherUrl, new Callback() {

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    LogUtil.d(TAG, "responseText: " + responseText);
                    final GsonHFWeather weather = GsonUtility.handleWeatherResponse(responseText);
                    if (weather != null && "ok".equals(weather.status)) { // 从服务器获取天气数据成功
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather", responseText);
                        editor.apply();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 更新Bing图片
     */
    private void updateBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
