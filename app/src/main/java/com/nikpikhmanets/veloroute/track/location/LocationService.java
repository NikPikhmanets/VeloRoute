package com.nikpikhmanets.veloroute.track.location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LocationService extends Service {

    private String TAG = "VeloRoute";

    GPSListener mGPSListener;
    LoggerNotification mLoggerNotification;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand/LocationService");
        mLoggerNotification.startLogging(0, 2, true, 1);

        if(intent != null) {
            mGPSListener = new GPSListener(this, mLoggerNotification);
            mGPSListener.onCreate(this);
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate/LocationService");
        if (mLoggerNotification == null) {
            mLoggerNotification = new LoggerNotification(this);
        }
        mLoggerNotification.stopLogging();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy/LocationService");

        mLoggerNotification.stopLogging();
        mLoggerNotification = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
