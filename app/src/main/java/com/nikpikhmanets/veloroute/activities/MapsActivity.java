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
import com.nikpikhmanets.veloroute.place.Place;
import com.nikpikhmanets.veloroute.place.PlaceListSingle;
import com.nikpikhmanets.veloroute.place.PlaceViewDialog;
import com.nikpikhmanets.veloroute.route.Route;
import com.nikpikhmanets.veloroute.route.RouteBuild;
import com.nikpikhmanets.veloroute.utils.GoogleMapsUtils;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        route = getIntent().getParcelableExtra(INTENT_ROUTE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mapStyle = prefs.getString(getString(R.string.default_map_style), "");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        RouteBuild br = new RouteBuild(this, mMap);
        br.parseGpxFile(route);

        GoogleMapsUtils.setTypeMaps(mapStyle, googleMap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_marker:
                mMap.clear();
                break;
            case show_my_location:
                myLocation();
                break;
            case record_track:
                recordTrack();
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

    private void recordTrack() {

    }

    private void myLocation() {

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//        }
    }

    private void setTypeGoogleMap(int type) {
        mMap.setMapType(type);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        List<Place> list = PlaceListSingle.getListPlace();
        for(int i = 0; i < list.size(); i++) {
            if (marker.getTitle().equals(list.get(i).getName())){
                Intent intent = new Intent(this, PlaceViewDialog.class);
                intent.putExtra("img", list.get(i).getImageList().get(0));
                intent.putExtra("txt", list.get(i).getDescription());
                startActivity(intent);
            }
        }
        return true;
    }
}
