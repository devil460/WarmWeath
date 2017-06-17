package com.ryan.warmweather;

import android.app.Application;
import android.content.Context;

import com.ryan.warmweather.util.DatabaseUtil;

import org.litepal.LitePalApplication;

/**
 * Created by Think on 2017/6/16.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        LitePalApplication.initialize(context);
    }
    public static Context getContext(){
        return context;
    }

}
