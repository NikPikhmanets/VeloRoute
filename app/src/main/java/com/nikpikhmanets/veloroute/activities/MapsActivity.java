package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.route.BuildRoute;

import static com.nikpikhmanets.veloroute.R.id.no_map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private PolylineOptions rectOptions;
    private String gpxFile;

    private String mapStyle;
    private String widthLineMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        gpxFile = intent.getStringExtra("gpx");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mapStyle = prefs.getString(getString(R.string.default_map_style), "");
        widthLineMap = prefs.getString(getString(R.string.width_line), "");
    }

    private void setPrefMapStyle(String mapStyle) {
        if (mapStyle.equals("no_map")) {
            setTypeGoogleMap(GoogleMap.MAP_TYPE_NONE);
        }
        if (mapStyle.equals("normal")){
            setTypeGoogleMap(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (mapStyle.equals("terrain")){
            setTypeGoogleMap(GoogleMap.MAP_TYPE_TERRAIN);
        }
        if (mapStyle.equals("satellite")) {
            setTypeGoogleMap(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (mapStyle.equals("hybrid")){
            setTypeGoogleMap(GoogleMap.MAP_TYPE_HYBRID);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        BuildRoute br = new BuildRoute(this);
        rectOptions = br.getPolylineOptionsRout(gpxFile);

        setPrefMapStyle(mapStyle);
        setWidthLineOnMap(Integer.parseInt(widthLineMap));

        mMap.addPolyline(rectOptions);
        mMap.moveCamera(CameraUpdateFactory.zoomIn());
        mMap.setOnMarkerClickListener(this);
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
        }
        return super.onOptionsItemSelected(item);
    }
    private void setTypeGoogleMap(int type) {
        mMap.setMapType(type);
    }

    private void setWidthLineOnMap(int widthLineOnMap) {
        rectOptions.width(widthLineOnMap);
    }

    private void setColorLineOnMap(int colorLineOnMap) {
        rectOptions.color(colorLineOnMap);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
