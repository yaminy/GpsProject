package com.yamin.gpsproject;

import android.app.Application;
import android.content.Context;

/**
 * Created by Yamin on 8/24/2018.
 */

public class GpsProjectApp extends Application{
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext();

    }
}
