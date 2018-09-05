package com.yamin.gpsproject.data.network.header;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yamin.gpsproject.GpsProjectApp;
import com.yamin.gpsproject.data.network.header.ApiEndPoint;
import com.yamin.gpsproject.data.network.header.ApiHeader;
import com.yamin.gpsproject.data.prefrences.AppPreferencesHelper;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIBaseCreator {
    public static ApiHeader getApiHeader() {

        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(GpsProjectApp.context);
        String REST_API_BASE_URL = ApiEndPoint.getRestApiBaseUrl();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(REST_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ApiHeader.class);


    }
}
