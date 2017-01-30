package com.nikpikhmanets.veloroute;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.nikpikhmanets.veloroute.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps, menu);
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng monast = new LatLng(49.153743, 32.2545142);

        mMap.addMarker(new MarkerOptions().position(monast).title("Monastyr"));


        mMap.addMarker(new MarkerOptions().position(new LatLng(49.155061, 32.253998)).icon(
                BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .title("джерело"));

        mMap.addMarker(new MarkerOptions().position(new LatLng(49.153232, 32.258328)).icon(
                BitmapDescriptorFactory.defaultMarker())
                .title("гайдамацький став"));

        mMap.addMarker(new MarkerOptions().position(new LatLng(49.154988, 32.242032)).icon(
                BitmapDescriptorFactory.defaultMarker())
                .title("памятник партизанам"));

        mMap.addMarker(new MarkerOptions().position(new LatLng(49.145740, 32.258490)).icon(
                BitmapDescriptorFactory.defaultMarker())
                .title("Козацький склик"));

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monast, 15));
    }
}
