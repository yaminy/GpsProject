package com.yamin.gpsproject.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.yamin.gpsproject.R;
import com.yamin.gpsproject.data.AppDataManager;
import com.yamin.gpsproject.data.network.model.GpsModel;
import com.yamin.gpsproject.data.prefrences.AppPreferencesHelper;
import com.yamin.gpsproject.data.service.BackgroundService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnStop)
    ImageView stopBtn;
    @BindView(R.id.btnStart)
    ImageView startBtn;
    @BindView(R.id.btnRstOil)
    CardView rstOilBtn;
    @BindView(R.id.btnRstBreak)
    CardView rstBreakBtn;
    @BindView(R.id.btnRstWheel)
    CardView rstWheelBtn;
    @BindView(R.id.remOil)
    TextView remOil;
    @BindView(R.id.remBreak)
    TextView remBreak;
    @BindView(R.id.remWheel)
    TextView remWheel;
    @BindView(R.id.txtStart)
    TextView txtStart;
    @BindView(R.id.txtStop)
    TextView txtStop;

    Call<GpsModel> call;
    Context context;
    AppPreferencesHelper appPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        appPreferencesHelper = new AppPreferencesHelper(context);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(context, BackgroundService.class));
                Toast.makeText(context, "Service Started", Toast.LENGTH_SHORT).show();
                startBtn.setVisibility(View.GONE);
                txtStart.setVisibility(View.GONE);
                stopBtn.setVisibility(View.VISIBLE);
                txtStop.setVisibility(View.VISIBLE);


            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBtn.setVisibility(View.VISIBLE);
                txtStart.setVisibility(View.VISIBLE);
                stopBtn.setVisibility(View.GONE);
                txtStop.setVisibility(View.GONE);
                stopService(new Intent(context, BackgroundService.class));
                Intent intent = new Intent();
                intent.setAction("com.example.alarm.notifier");
                sendBroadcast(intent);
                Toast.makeText(context, "Service Stopped", Toast.LENGTH_SHORT).show();


            }
        });
        rstOilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPreferencesHelper.setSumOil(0);
                remOil.setText(String.valueOf(50));


            }
        });
        rstBreakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPreferencesHelper.setSumBreak(0);
                remBreak.setText(String.valueOf(100));

            }
        });
        rstWheelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPreferencesHelper.setSumWheel(0);
                remWheel.setText(String.valueOf(150));

            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction("update");
        BroadcastReceiver updateUIReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int remainOil = (int) (5000 - appPreferencesHelper.getSumOil());
                remOil.setText(String.valueOf(remainOil));
                int remainBreak = (int) (10000 - appPreferencesHelper.getSumBreak());
                remBreak.setText(String.valueOf(remainBreak));
                int remainWheel = (int) (15000 - appPreferencesHelper.getSumWheel());
                remWheel.setText(String.valueOf(remainWheel));
            }
        };
        registerReceiver(updateUIReceiver, filter);


//        call = AppDataManager.getInstance(this).getGpsCoordinates(new Callback<GpsModel>() {
//            @Override
//            public void onResponse(Call<GpsModel> call, Response<GpsModel> response) {
//                if (response.isSuccessful()) {
//                    GpsModel gpsModel = response.body();
//                } else {
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GpsModel> call, Throwable t) {
//                String dffd;
//            }
//        });


    }


}
