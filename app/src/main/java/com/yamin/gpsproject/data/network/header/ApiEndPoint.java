package com.yamin.gpsproject.data.network.header;

public class ApiEndPoint {
    private  static String REST_API_BASE_URL= "http://192.168.4.1";


    public static String getRestApiBaseUrl() {
        return REST_API_BASE_URL;
    }

    public static void setRestApiBaseUrl(String restApiBaseUrl) {
        REST_API_BASE_URL = restApiBaseUrl;
    }

}
