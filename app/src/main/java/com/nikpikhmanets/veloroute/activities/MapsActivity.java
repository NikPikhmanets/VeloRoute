package com.nikpikhmanets.veloroute.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.gpx.data.GPXDocument;
import com.nikpikhmanets.veloroute.waypoint.WayPoint;

import java.util.ArrayList;
import java.util.List;

import static com.nikpikhmanets.veloroute.R.id.no_map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private List<WayPoint> wayPointList = new ArrayList<>();

    public PolylineOptions rectOptions = new PolylineOptions();
    public static PolylineOptions polylineOptions = null;
    public static GPXDocument mDocument = null;

    private String mapStyle;
    private String widthLineMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        Intent intent = getIntent();
//        gpxFile = intent.getStringExtra("gpx");

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

        rectOptions = polylineOptions;

        buildWayPoints();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (wayPointList.size() != 0) {
            for (int i = 0; i < wayPointList.size(); i++) {

                LatLng latLon = new LatLng(wayPointList.get(i).getLat(), wayPointList.get(i).getLon());
                mMap.addMarker(new MarkerOptions()
                        .position(latLon)
                        .title(wayPointList.get(i).getName())
                        .snippet(wayPointList.get(i).getDescription())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_water)));

                builder.include(latLon);
            }
        }

        if (!mapStyle.isEmpty()) {
            setPrefMapStyle(mapStyle);
        } else
            setTypeGoogleMap(GoogleMap.MAP_TYPE_NORMAL);

        if (!widthLineMap.isEmpty()) {
            setWidthLineOnMap(Integer.parseInt(widthLineMap));
        } else
            rectOptions.width(5);

        if (rectOptions != null)
            mMap.addPolyline(rectOptions);

//        LatLngBounds bounds = builder.build();
//        int padding = 0; // offset from edges of the map in pixels
//        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//        googleMap.animateCamera(cu);

//        mMap.moveCamera(CameraUpdateFactory.zoomIn());
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

    private void buildWayPoints() {
        for (int x = 0; x < mDocument.getWayPoints().size(); x++) {
            WayPoint wayPoint = new WayPoint();
            wayPoint.setLat(mDocument.getWayPoints().get(x).getLatitude());
            wayPoint.setLon(mDocument.getWayPoints().get(x).getLongitude());
            wayPoint.setDescription(mDocument.getWayPoints().get(x).getDescription());
            wayPoint.setName(mDocument.getWayPoints().get(x).getName());
            wayPointList.add(wayPoint);
        }
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
