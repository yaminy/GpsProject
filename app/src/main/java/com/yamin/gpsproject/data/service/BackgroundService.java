package com.yamin.gpsproject.data.service;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.yamin.gpsproject.data.prefrences.AppPreferencesHelper;
import com.yamin.gpsproject.ui.main.MainActivity;
import com.yamin.gpsproject.utils.MyNotificationManagers;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yamin on 8/24/2018.
 */

public class BackgroundService extends Service {
    private static final String TAG = BackgroundService.class.getSimpleName();
    Context context;
    AppPreferencesHelper appPreferencesHelper;
    ScheduledExecutorService scheduleTaskExecutor;
    float distance = 0;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        requestLocationUpdates();
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "received stop broadcast");
            // Stop the service when the notification is tapped
            unregisterReceiver(stopReceiver);
            stopSelf();
        }
    };

    private void requestLocationUpdates() {
        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                appPreferencesHelper = new AppPreferencesHelper(context);
                Log.d("BackgroundGPSService", "Service RUNNED!");
                FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        int permission = ContextCompat.checkSelfPermission(context,
                                Manifest.permission.ACCESS_FINE_LOCATION);
                        if (permission == PackageManager.PERMISSION_GRANTED) {
                            try {
                                Location currentLocation = new Location("");
                                currentLocation.setLatitude(task.getResult().getLatitude());
                                currentLocation.setLongitude(task.getResult().getLongitude());
                                if (appPreferencesHelper.getLat() == 0 || appPreferencesHelper.getLng() == 0) {
                                    distance = 0;
                                } else {
                                    Location location = new Location("");
                                    location.setLatitude(appPreferencesHelper.getLat());
                                    location.setLongitude(appPreferencesHelper.getLng());
                                    distance = currentLocation.distanceTo(location);
                                }
                                float sumOil = appPreferencesHelper.getSumOil() + distance;
                                float sumBreak = appPreferencesHelper.getSumBreak() + distance;
                                float sumWheel = appPreferencesHelper.getSumWheel() + distance;
                                Toast.makeText(context, "Latitude is : " + String.valueOf(task.getResult().getLatitude())
                                        + "\n Longitude is : " + String.valueOf(task.getResult().getLongitude()), Toast.LENGTH_SHORT).show();

                                appPreferencesHelper.setLat((float) task.getResult().getLatitude());
                                appPreferencesHelper.setLng((float) task.getResult().getLongitude());
                                if (distance > 10) {
                                    appPreferencesHelper.setSumOil(sumOil);
                                    appPreferencesHelper.setSumBreak(sumBreak);
                                    appPreferencesHelper.setSumWheel(sumWheel);
                                }
                                if (sumOil >= 5000) {
                                    MyNotificationManagers.showNotification("یادآوری", "زمان تعویض روغن موتور خودرو شما فرا رسیده .", MainActivity.class, context);
                                }
                                if (sumBreak >= 10000) {
                                    MyNotificationManagers.showNotification("یادآوری", "زمان تعویض لنت ترمز خودرو شما فرا رسیده .", MainActivity.class, context);
                                }
                                if (sumWheel >= 15000) {
                                    MyNotificationManagers.showNotification("یادآوری", "زمان تعویض لاستیک های خودرو شما فرا رسیده .", MainActivity.class, context);
                                }

                                Intent local = new Intent();
                                local.setAction("update");
                                context.sendBroadcast(local);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });


            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduleTaskExecutor.shutdown();
        stopSelf();
    }


}

