package com.nikpikhmanets.veloroute.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.place.Place;
import com.nikpikhmanets.veloroute.place.PlaceListSingle;
import com.nikpikhmanets.veloroute.place.PlaceViewDialog;
import com.nikpikhmanets.veloroute.route.Route;
import com.nikpikhmanets.veloroute.route.RouteBuild;
import com.nikpikhmanets.veloroute.track.location.TrackLocationManager;
import com.nikpikhmanets.veloroute.utils.GoogleMapsUtils;
import com.nikpikhmanets.veloroute.utils.PermissionUtils;

import java.util.List;

import static com.nikpikhmanets.veloroute.R.id.map;
import static com.nikpikhmanets.veloroute.R.id.no_map;
import static com.nikpikhmanets.veloroute.R.id.record_track;
import static com.nikpikhmanets.veloroute.R.id.show_my_location;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    final String INTENT_ROUTE = "ROUTE";

    private GoogleMap mMap;
    private String mapStyle;
    private Route route;

    MenuItem menuEnableGpsItem;

    TrackLocationManager trackLocationManager;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        trackLocationManager = new TrackLocationManager(this);

        route = getIntent().getParcelableExtra(INTENT_ROUTE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mapStyle = prefs.getString(getString(R.string.default_map_style), "normal");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.setOnMarkerClickListener(this);

        trackLocationManager.setMap(googleMap);

        RouteBuild br = new RouteBuild(this, mMap);
        br.parseGpxFile(route);

        GoogleMapsUtils.setTypeMaps(mapStyle, googleMap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_menu, menu);

        this.menuEnableGpsItem = menu.findItem(R.id.show_my_location);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_marker:
                mMap.clear();
                break;
            case show_my_location:
                trackLocationManager.enabledLocation(item);
                break;
            case record_track:
                trackLocationManager.recordTrack();
                break;
            case no_map:
                setTypeGoogleMap(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.normal_map:
                setTypeGoogleMap(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite_map:
                setTypeGoogleMap(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.terrain_map:
                setTypeGoogleMap(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.hybrid_map:
                setTypeGoogleMap(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

//            enableMyLocation();
        } else {
            mPermissionDenied = true;
        }
    }

    private void setTypeGoogleMap(int type) {
        mMap.setMapType(type);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        List<Place> list = PlaceListSingle.getListPlace();
        for (int i = 0; i < list.size(); i++) {
            if (marker.getTitle().equals(list.get(i).getName())) {
                Intent intent = new Intent(this, PlaceViewDialog.class);
                intent.putExtra("img", list.get(i).getImageList().get(0));
                intent.putExtra("txt", list.get(i).getDescription());
                startActivity(intent);
            }
        }
        return true;
    }
}
