package com.nikpikhmanets.veloroute.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.route.BuildRoute;
import com.nikpikhmanets.veloroute.waypoint.BuildWayPoint;

import static com.nikpikhmanets.veloroute.R.id.googleMap;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private MapView mapView;

    private Context context;

    BuildWayPoint wayPoint;
    BuildRoute route;

    final String BUNDLE_KEY_FILE_NAME_GPX = "name_file_gpx";
    final String BUNDLE_KEY_TYPE_GPX = "type_file_gpx";

    final String BUNDLE_VALUE_WAY_POINTS = "way_points";
    final String BUNDLE_VALUE_ROUTE = "route";

    private String typeFileGpx;
    private String nameFileGpx;

    public MapsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_google_maps, container, false);

        context = getActivity();

        mapView = (MapView) v.findViewById(googleMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Bundle bundle = getArguments();
        extractParamFromBundle(bundle);
        return v;
    }

    private void extractParamFromBundle(Bundle bundle) {
        if (bundle != null) {
            typeFileGpx = bundle.getString(BUNDLE_KEY_TYPE_GPX);
            nameFileGpx = bundle.getString(BUNDLE_KEY_FILE_NAME_GPX);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        createWayPoint(googleMap);
        createRoute(googleMap);

//        LatLngBounds bounds = builder.build();
//        int padding = 0; // offset from edges of the map in pixels
//        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//        googleMap.animateCamera(cu);


//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        LatLng monast = new LatLng(49.153743, 32.2545142);
//
//        mMap.addMarker(new MarkerOptions().position(monast).title("Monastyr"));
//
//
//        mMap.addMarker(new MarkerOptions().position(new LatLng(49.155061, 32.253998)).icon(
//                BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
//                .title("джерело"));
//
//        mMap.addMarker(new MarkerOptions().position(new LatLng(49.153232, 32.258328)).icon(
//                BitmapDescriptorFactory.defaultMarker())
//                .title("гайдамацький став"));
//
//        mMap.addMarker(new MarkerOptions().position(new LatLng(49.154988, 32.242032)).icon(
//                BitmapDescriptorFactory.defaultMarker())
//                .title("памятник партизанам"));
//
//        mMap.addMarker(new MarkerOptions().position(new LatLng(49.145740, 32.258490)).icon(
//                BitmapDescriptorFactory.defaultMarker())
//                .title("Козацький склик"))
//                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.kozak_resize));
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monast, 15));
//        mMap.setOnMarkerClickListener(this);

//        BuildRoute br = new BuildRoute(getContext(), fileName);
//        PolylineOptions rectOptions = br.getPolylineOptionsRout("");
//        mMap.addPolyline(rectOptions);
    }

    private void createWayPoint(GoogleMap googleMap) {
        if (typeFileGpx != null && typeFileGpx.equals(BUNDLE_VALUE_WAY_POINTS) && nameFileGpx != null) {
            wayPoint = new BuildWayPoint(context, googleMap);
            wayPoint.parseGpxFile(nameFileGpx);
        }
    }

    private void createRoute(GoogleMap googleMap) {
        if (typeFileGpx != null && typeFileGpx.equals(BUNDLE_VALUE_ROUTE) && nameFileGpx != null) {
            route = new BuildRoute(context);
            route.parseGpxFile(nameFileGpx);
        }
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
        if (marker.getTitle().equals("Козацький склик")) // if marker source is clicked
        {
            Toast.makeText(getContext(), "!!", Toast.LENGTH_SHORT).show();
        }

        return true;
    }


}
