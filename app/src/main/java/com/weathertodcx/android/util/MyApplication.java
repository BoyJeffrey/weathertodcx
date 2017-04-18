package com.weathertodcx.android.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.litepal.LitePalApplication;

/**
 * <p>获取app级Context</p>
 * Created by Administrator on 2017/4/18.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        LitePalApplication.initialize(context);
        LogUtil.d(TAG, "onCreate: ");
    }

    public static Context getContext() {
        return context;
    }
}
