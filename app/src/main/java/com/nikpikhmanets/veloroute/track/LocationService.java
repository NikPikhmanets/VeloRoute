package com.nikpikhmanets.veloroute.track;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;


public class LocationService extends GcmTaskService {
    private static final String TAG = LocationService.class.getSimpleName();

    private Context context;

    private LocationManager locationManager;
    private Location mCurrentLocation;

    public static final String TASK_GET_LOCATION_ONCE="location_oneoff_task";
    public static final String TASK_GET_LOCATION_PERIODIC="location_periodic_task";


    private static final int RC_PLAY_SERVICES = 123;


    @Override
    public void onInitializeTasks() {
        // When your package is removed or updated, all of its network tasks are cleared by
        // the GcmNetworkManager. You can override this method to reschedule them in the case of
        // an updated package. This is not called when your application is first installed.
        //
        // This is called on your application's main thread.
        startPeriodicLocationTask(TASK_GET_LOCATION_PERIODIC,
                30L, null);
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        Log.d(TAG, "onRunTask: " + taskParams.getTag());

        String tag = taskParams.getTag();
        Bundle extras = taskParams.getExtras();
        // Default result is success.
        int result = GcmNetworkManager.RESULT_SUCCESS;

        switch (tag) {
            case TASK_GET_LOCATION_ONCE:
                getLastKnownLocation();
                break;

            case TASK_GET_LOCATION_PERIODIC:
                getLastKnownLocation();
                break;
        }
        return result;
    }


    public void getLastKnownLocation() {
        Location lastKnownGPSLocation;
        Location lastKnownNetworkLocation;
        String gpsLocationProvider = LocationManager.GPS_PROVIDER;
        String networkLocationProvider = LocationManager.NETWORK_PROVIDER;

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

            lastKnownNetworkLocation = locationManager.getLastKnownLocation(networkLocationProvider);
            lastKnownGPSLocation = locationManager.getLastKnownLocation(gpsLocationProvider);

            if (lastKnownGPSLocation != null) {
                Log.i(TAG, "lastKnownGPSLocation is used.");
                this.mCurrentLocation = lastKnownGPSLocation;
            } else if (lastKnownNetworkLocation != null) {
                Log.i(TAG, "lastKnownNetworkLocation is used.");
                this.mCurrentLocation = lastKnownNetworkLocation;
            } else {
                Log.e(TAG, "lastLocation is not known.");
            }

//            LocationChangedEvent event = new LocationChangedEvent();
//            event.setLocation(mCurrentLocation);
//            EventHelper.publishEvent(event);

        } catch (SecurityException sex) {
            Log.e(TAG, "Location permission is not granted!");
        }
    }

    public void startOneOffLocationTask(String tag, Bundle extras) {
        Log.d(TAG, "startOneOffLocationTask");

        GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(context);
        OneoffTask.Builder taskBuilder = new OneoffTask.Builder()
                .setService(LocationService.class)
                .setTag(tag);

        if (extras != null) taskBuilder.setExtras(extras);

        OneoffTask task = taskBuilder.build();
        mGcmNetworkManager.schedule(task);
    }

    public void startPeriodicLocationTask(String tag, Long period, Bundle extras) {
        Log.d(TAG, "startPeriodicLocationTask");

        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(context);
        PeriodicTask.Builder taskBuilder = new PeriodicTask.Builder()
                .setService(LocationService.class)
                .setTag(tag)
                .setPeriod(period)
                .setPersisted(true)
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED);

        if (extras != null) taskBuilder.setExtras(extras);

        PeriodicTask task = taskBuilder.build();
        mGcmNetworkManager.schedule(task);
    }




    public boolean checkPlayServicesAvailable(Activity activity) {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int resultCode = availability.isGooglePlayServicesAvailable(context);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (availability.isUserResolvableError(resultCode)) {
                // Show dialog to resolve the error.
                availability.getErrorDialog(activity, resultCode, RC_PLAY_SERVICES).show();
            }
            return false;
        } else {
            return true;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}