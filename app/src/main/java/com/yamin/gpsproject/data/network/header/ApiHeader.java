package com.yamin.gpsproject.data.network.header;


import com.yamin.gpsproject.data.network.model.GpsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiHeader {

    @GET("/")
    Call<GpsModel> getGpsCoordinates();

}

