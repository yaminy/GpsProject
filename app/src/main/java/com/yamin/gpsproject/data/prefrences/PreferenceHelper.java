package com.yamin.gpsproject.data.prefrences;

public interface PreferenceHelper {

    void setLat(float lat);
    void setLng(float lng);
    void setSumOil(float sumOil);
    void setSumBreak(float sumBreak);
    void setSumWheel(float sumWheel);
    float getLat();
    float getLng();
    float getSumOil();
    float getSumBreak();
    float getSumWheel();


}
