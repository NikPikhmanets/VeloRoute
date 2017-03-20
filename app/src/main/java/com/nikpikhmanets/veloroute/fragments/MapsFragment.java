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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.waypoint.WayPointBuild;

import static com.nikpikhmanets.veloroute.R.id.googleMap;

public class MapsFragment extends  Fragment implements OnMapReadyCallback {

    final String BUNDLE_KEY_FILE_NAME_GPX = "name_file_gpx";
    final String BUNDLE_KEY_TYPE_GPX = "type_file_gpx";
    final String BUNDLE_KEY_TITLE = "title";
    final String BUNDLE_VALUE_WAY_POINTS = "way_points";
    final String BUNDLE_VALUE_ROUTE = "route";
    WayPointBuild wayPoint;

    private GoogleMap mMap;
    private MapView mapView;
    private Context context;
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
            String title = bundle.getString(BUNDLE_KEY_TITLE);
            if (title != null)
                getActivity().setTitle(title);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        createWayPoint(googleMap);
    }

    private void createWayPoint(GoogleMap googleMap) {
        if (typeFileGpx != null && typeFileGpx.equals(BUNDLE_VALUE_WAY_POINTS) && nameFileGpx != null) {
            wayPoint = new WayPointBuild(context, googleMap);
            wayPoint.parseGpxFile(nameFileGpx);
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
//        inflater.inflate(R.menu.maps_menu, menu);
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
}
