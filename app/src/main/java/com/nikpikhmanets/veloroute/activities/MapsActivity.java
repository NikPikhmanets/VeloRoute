package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.nikpikhmanets.veloroute.route.RouteBuild;

import static com.nikpikhmanets.veloroute.R.id.no_map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private String gpxFile;
    private String listPlace;
    private String mapStyle;

    final String INTENT_GPX = "gpx";
    final String INTENT_LIST_PLACE = "list_place";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        gpxFile = intent.getStringExtra(INTENT_GPX);
        listPlace = intent.getStringExtra(INTENT_LIST_PLACE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mapStyle = prefs.getString(getString(R.string.default_map_style), "");
    }

    private void setPrefMapStyle(String mapStyle) {
        if (mapStyle.equals("no_map")) {
            setTypeGoogleMap(GoogleMap.MAP_TYPE_NONE);
        }
        if (mapStyle.equals("normal")) {
            setTypeGoogleMap(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (mapStyle.equals("terrain")) {
            setTypeGoogleMap(GoogleMap.MAP_TYPE_TERRAIN);
        }
        if (mapStyle.equals("satellite")) {
            setTypeGoogleMap(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (mapStyle.equals("hybrid")) {
            setTypeGoogleMap(GoogleMap.MAP_TYPE_HYBRID);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (!gpxFile.isEmpty()) {
            RouteBuild br = new RouteBuild(this, mMap);
            br.parseGpxFile(gpxFile, listPlace);
        }
        if (!mapStyle.isEmpty()) {
            setPrefMapStyle(mapStyle);
        } else
            setTypeGoogleMap(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    private void setTypeGoogleMap(int type) {
        mMap.setMapType(type);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
