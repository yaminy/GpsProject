package com.yamin.gpsproject.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GpsModel {

    @SerializedName("altitude")
    @Expose
    private Double altitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("sats_in_use")
    @Expose
    private int satsInUse;

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getSatsInUse() {
        return satsInUse;
    }

    public void setSatsInUse(int satsInUse) {
        this.satsInUse = satsInUse;
    }
}
