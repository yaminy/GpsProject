package com.yamin.gpsproject.data;

import android.content.Context;

import com.google.gson.JsonPrimitive;
import com.yamin.gpsproject.data.network.header.APIBaseCreator;
import com.yamin.gpsproject.data.network.header.ApiHeader;
import com.yamin.gpsproject.data.network.model.GpsModel;
import com.yamin.gpsproject.data.prefrences.AppPreferencesHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppDataManager implements DataManagerHelper {
    private final AppPreferencesHelper appPreferencesHelper;
    private static AppDataManager appDataManager;
    private final ApiHeader apiHeader;

    public static AppDataManager getInstance(Context context) {
        if (appDataManager == null) {
            appDataManager = new AppDataManager(AppPreferencesHelper.getInstance(context), APIBaseCreator.getApiHeader());
        }
        return appDataManager;
    }

    public AppDataManager(AppPreferencesHelper appPreferencesHelper, ApiHeader apiHeader) {
        this.appPreferencesHelper = appPreferencesHelper;
        this.apiHeader = apiHeader;
    }

    public AppDataManager(Context context) {
        this.appPreferencesHelper = AppPreferencesHelper.getInstance(context);
        this.apiHeader = null;
    }

    public Call<GpsModel> getGpsCoordinates(final Callback<GpsModel> callback) {

        Call<GpsModel> call = APIBaseCreator.getApiHeader().getGpsCoordinates();
        call.enqueue(new Callback<GpsModel>() {
            @Override
            public void onResponse(Call<GpsModel> call, Response<GpsModel> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Exception());
                }
            }

            @Override
            public void onFailure(Call<GpsModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
        return call;
    }



    /**
     * PREFERENCES IS HERE
     */
}
