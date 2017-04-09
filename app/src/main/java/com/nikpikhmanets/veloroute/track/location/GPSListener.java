package com.nikpikhmanets.veloroute.track.location;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.nikpikhmanets.veloroute.App;

public class GPSListener implements LocationListener {

    private static final String TAG = "VeloRoute";
    LocationManager mLocationManager;

    private final LocationService mService;
    private final LoggerNotification mLoggerNotification;

    public GPSListener(LocationService locService, LoggerNotification loggerNotification) {
        this.mService = locService;
        this.mLoggerNotification = loggerNotification;
    }

    public void onCreate(Context cntx) {
        Log.d(TAG, "onCreate/GPSListener");

        mLocationManager = (LocationManager) mService.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(cntx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(cntx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, this);
        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged/GPSListener");
//        if (mLoggerNotification.isShowingDisabled()) {
//            mLoggerNotification.stopDisabledProvider(R.string.service_gpsenabled);
//        }

        broadcastLocation(location);
        storeLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
//        if (mPrecision != ServiceConstants.LOGGING_GLOBAL && provider.equals(LocationManager.GPS_PROVIDER)) {
//            mLoggerNotification.stopDisabledProvider(R.string.service_gpsenabled);
//            mStartNextSegment = true;
//        } else if (mPrecision == ServiceConstants.LOGGING_GLOBAL && provider.equals(LocationManager.NETWORK_PROVIDER)) {
//            mLoggerNotification.stopDisabledProvider(R.string.service_dataenabled);
//        }
    }

    @Override
    public void onProviderDisabled(String provider) {
//        if (mPrecision != ServiceConstants.LOGGING_GLOBAL && provider.equals(LocationManager.GPS_PROVIDER)) {
//            mLoggerNotification.startDisabledProvider(R.string.service_gpsdisabled, mTrackId);
//        } else if (mPrecision == ServiceConstants.LOGGING_GLOBAL && provider.equals(LocationManager.NETWORK_PROVIDER)) {
//            mLoggerNotification.startDisabledProvider(R.string.service_datadisabled, mTrackId);
//        }
    }

    private void broadcastLocation(Location location) {
        Intent intent = new Intent(TrackLocationManager.BROADCAST_ACTION_USER_LOCATION);
        intent.putExtra(TrackLocationManager.EXTRA_LOCATION, location);
        mService.sendBroadcast(intent);
    }

    private void storeLocation(Location location) {

        if(App.FLAG_SAVE_TRACK){

            ContentValues args = new ContentValues();
        }
//        if (!isLogging() || mTrackId < 0 || mSegmentId < 0) {
//            Timber.e(String.format("Storing location without Logging (%b) or track (%d,%d).", isLogging(), mTrackId, mSegmentId));
//            return;
//        } else {
//            Timber.e(String.format("Storing location track/segment (%d,%d).", mTrackId, mSegmentId));
//        }
//        ContentValues args = new ContentValues();
//
//        args.put(ContentConstants.Waypoints.LATITUDE, Double.valueOf(location.getLatitude()));
//        args.put(ContentConstants.Waypoints.LONGITUDE, Double.valueOf(location.getLongitude()));
//        args.put(ContentConstants.Waypoints.SPEED, Float.valueOf(location.getSpeed()));
//        args.put(ContentConstants.Waypoints.TIME, Long.valueOf(System.currentTimeMillis()));
//        if (location.hasAccuracy()) {
//            args.put(ContentConstants.Waypoints.ACCURACY, Float.valueOf(location.getAccuracy()));
//        }
//        if (location.hasAltitude()) {
//            args.put(ContentConstants.Waypoints.ALTITUDE, Double.valueOf(location.getAltitude()));
//
//        }
//        if (location.hasBearing()) {
//            args.put(ContentConstants.Waypoints.BEARING, Float.valueOf(location.getBearing()));
//        }
//
//        Uri waypointInsertUri = Uri.withAppendedPath(ContentConstants.Tracks.CONTENT_URI, mTrackId + "/segments/" + mSegmentId +
//                "/waypoints");
//        Uri inserted = mService.getContentResolver().insert(waypointInsertUri, args);
//        mWaypointId = Long.parseLong(inserted.getLastPathSegment());
    }

}
