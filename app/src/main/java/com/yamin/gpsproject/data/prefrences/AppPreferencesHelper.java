package com.yamin.gpsproject.data.prefrences;

import android.content.Context;
import android.content.SharedPreferences;

import com.yamin.gpsproject.GpsProjectApp;


public class AppPreferencesHelper implements PreferenceHelper {

    private static AppPreferencesHelper appPreferencesHelper;
    private SharedPreferences preferences;
    private Context context;

    public static AppPreferencesHelper getInstance(Context context) {
        if (appPreferencesHelper == null) {
            appPreferencesHelper = new AppPreferencesHelper(context);
        }
        return appPreferencesHelper;
    }

    public AppPreferencesHelper(Context context) {
        if (context == null) {
            context = GpsProjectApp.context;
        }
        preferences = context.getSharedPreferences("appPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @Override
    public float getLat() {
        return preferences.getFloat("LAT", 0);
    }

    @Override
    public void setLat(float lat) {
        preferences.edit()
                .putFloat("LAT", lat)
                .apply();
    }

    @Override
    public float getLng() {
        return preferences.getFloat("LNG", 0);
    }


    @Override
    public void setLng(float lng) {
        preferences.edit()
                .putFloat("LNG", lng)
                .apply();
    }

    @Override
    public void setSumOil(float sumOil) {
        preferences.edit()
                .putFloat("SOIL", sumOil)
                .apply();
    }

    @Override
    public void setSumBreak(float sumBreak) {
        preferences.edit()
                .putFloat("SBRK", sumBreak)
                .apply();

    }

    @Override
    public void setSumWheel(float sumWheel) {
        preferences.edit()
                .putFloat("SWHL", sumWheel)
                .apply();
    }

    @Override
    public float getSumOil() {
        return preferences.getFloat("SOIL", 0);
    }

    @Override
    public float getSumBreak() {
        return preferences.getFloat("SBRK", 0);

    }

    @Override
    public float getSumWheel() {
        return preferences.getFloat("SWHL", 0);
    }

}
