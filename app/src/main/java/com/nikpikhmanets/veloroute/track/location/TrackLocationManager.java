package com.nikpikhmanets.veloroute.track.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.activities.MapsActivity;

public class TrackLocationManager {

    private Marker marker;
    private GoogleMap mMap;

    private LinearLayout infoTrackLayout;
    private LinearLayout statusGpsLayout;
    private TextView textStatusGps;

    private GpsDataReceiver gpsDataReceiver = new GpsDataReceiver();

    final static String BROADCAST_ACTION_USER_LOCATION = "BROADCAST_ACTION_USER_LOCATION";
    final static String EXTRA_LOCATION = "USER_LOCATION";

    private MapsActivity mapsActivity;

    public TrackLocationManager(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
        initView();
    }

    private void initView() {

        this.statusGpsLayout = (LinearLayout) mapsActivity.findViewById(R.id.statusLayout);
        this.textStatusGps = (TextView) mapsActivity.findViewById(R.id.textStatusGps);
        this.infoTrackLayout = (LinearLayout) mapsActivity.findViewById(R.id.infoTrackLayout);
    }

    public void enabledLocation(MenuItem mItem) {

        if (mItem.getTitle().equals(mapsActivity.getString(R.string.disable_gps))) {
            Log.d("VeloRoute", "stopService");

            statusGpsLayout.setVisibility(View.INVISIBLE);
            textStatusGps.setText("");

            mapsActivity.stopService(new Intent(mapsActivity, LocationService.class));
            mapsActivity.unregisterReceiver(gpsDataReceiver);
            deleteMarkerUserLocation();
            mItem.setTitle(R.string.enable_gps);
        } else {
            Log.d("VeloRoute", "startService");

            statusGpsLayout.setVisibility(View.VISIBLE);
            textStatusGps.setText(R.string.search_gps_signal);

            mapsActivity.registerReceiver(gpsDataReceiver, new IntentFilter(BROADCAST_ACTION_USER_LOCATION));
            mapsActivity.startService(new Intent(mapsActivity, LocationService.class));
            mItem.setTitle(R.string.disable_gps);
        }
    }

    public void recordTrack(){

    }

    private void setMarkerUserLocation(Location location) {

        if (mMap != null) {
            if(marker != null)
                deleteMarkerUserLocation();

            statusGpsLayout.setVisibility(View.INVISIBLE);

            LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
            marker = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.puntor)));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        }
    }

    private void deleteMarkerUserLocation() {
        if (marker != null)
            marker.remove();
    }

    public void setMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    private class GpsDataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                Location location = intent.getParcelableExtra(EXTRA_LOCATION);
                setMarkerUserLocation(location);
            }
        }
    }


//    private Context context;
//
//    private LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
//
//    public TrackLocationManager(Context context) {
//        this.context = context;
//    }
//
//
//    void enable() {
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                1000 * 10, 10, locationListener);
//
//        locationManager.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
//                locationListener);
//    }
//
//
//    private LocationListener locationListener = new LocationListener() {
//
//        @Override
//        public void onLocationChanged(Location location) {
//            showLocation(location);
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//            checkEnabled();
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//            checkEnabled();
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            showLocation(locationManager.getLastKnownLocation(provider));
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            if (provider.equals(LocationManager.GPS_PROVIDER)) {
//                tvStatusGPS.setText("Status: " + String.valueOf(status));
//            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
//                tvStatusNet.setText("Status: " + String.valueOf(status));
//            }
//        }
//
//
//        private void showLocation(Location location) {
//            if (location == null)
//                return;
//            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
//                tvLocationGPS.setText(formatLocation(location));
//            } else if (location.getProvider().equals(
//                    LocationManager.NETWORK_PROVIDER)) {
//                tvLocationNet.setText(formatLocation(location));
//            }
//        }
//
//        private String formatLocation(Location location) {
//            if (location == null)
//                return "";
//            return String.format(
//                    "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
//                    location.getLatitude(), location.getLongitude(), new Date(
//                            location.getTime()));
//        }
//
//        private void checkEnabled() {
//            tvEnabledGPS.setText("Enabled: "
//                    + locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER));
//            tvEnabledNet.setText("Enabled: "
//                    + locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
//        }
//
//        public void onClickLocationSettings(View view) {
//            startActivity(new Intent(
//                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//        }
//
//
//    };
}
