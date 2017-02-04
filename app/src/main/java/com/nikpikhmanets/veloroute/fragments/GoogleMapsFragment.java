package com.nikpikhmanets.veloroute.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nikpikhmanets.veloroute.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private MapView mapView;


    public GoogleMapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_google_maps, container, false);

        mapView = (MapView) v.findViewById(R.id.googleMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this); //this is important

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

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
                .title("Козацький склик"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.kozak_resize));

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monast, 15));
        mMap.setOnMarkerClickListener(this);

//        PolylineOptions rectOptions = new PolylineOptions()
//                .add(new LatLng(37.35, -122.0))
//                .add(new LatLng(37.45, -122.0))  // North of the previous point, but at the same longitude
//                .add(new LatLng(37.45, -122.2))  // Same latitude, and 30km to the west
//                .add(new LatLng(37.35, -122.2))  // Same longitude, and 16km to the south
//                .add(new LatLng(37.35, -122.0)); // Closes the polyline.
//
//// Get back the mutable Polyline
//        Polyline polyline = mMap.addPolyline(rectOptions);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.maps_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.no_map:
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void setTypeGoogleMap(int type) {
        mMap.setMapType(type);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.getTitle().equals("Козацький склик")) // if marker source is clicked
        {
            Toast.makeText(getContext(), "!!", Toast.LENGTH_SHORT).show();
        }
            
        return true;
    }
}
