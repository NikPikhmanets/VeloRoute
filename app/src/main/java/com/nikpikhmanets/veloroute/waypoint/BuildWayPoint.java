package com.nikpikhmanets.veloroute.waypoint;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nikpikhmanets.veloroute.gpx.data.GPXDocument;
import com.nikpikhmanets.veloroute.gpx.xml.GpxParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BuildWayPoint implements GpxParser.GpxParserListener {

    private Context context;
    private GoogleMap map;
    private float colorMarker;

    private List<WayPoint> wayPointList = new ArrayList<>();
    private ProgressDialog mProgressDialog = null;

    public BuildWayPoint(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;
    }

    public void parseGpxFile(String gpxData) {

        if (gpxData.equals("water_points.gpx"))
            colorMarker = BitmapDescriptorFactory.HUE_AZURE;
        else
            colorMarker = BitmapDescriptorFactory.HUE_RED;

        AssetManager am = context.getAssets();
        InputStream input = null;
        try {
            input = am.open(gpxData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new GpxParser(input, this, null).parse();
    }

    @Override
    public void onGpxParseStarted() {
        mProgressDialog = ProgressDialog.show(context, "Open Route", "Started");
    }

    @Override
    public void onGpxParseCompleted(GPXDocument document) {
        mProgressDialog.dismiss();
        addArrayWayPoints(document);
        addMarkerToMapsFragment();
    }

    private void addArrayWayPoints(GPXDocument document) {
        for (int x = 0; x < document.getWayPoints().size(); x++) {
            WayPoint wayPoint = new WayPoint();
            wayPoint.setLat(document.getWayPoints().get(x).getLatitude());
            wayPoint.setLon(document.getWayPoints().get(x).getLongitude());
            wayPoint.setDescription(document.getWayPoints().get(x).getDescription());
            wayPoint.setName(document.getWayPoints().get(x).getName());
            wayPointList.add(wayPoint);
        }
    }

    private void addMarkerToMapsFragment() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (wayPointList.size() != 0) {
            for (int i = 0; i < wayPointList.size(); i++) {
                LatLng latLon = new LatLng(wayPointList.get(i).getLat(), wayPointList.get(i).getLon());
                map.addMarker(new MarkerOptions()
                        .position(latLon)
                        .title(wayPointList.get(i).getName())
                        .snippet(wayPointList.get(i).getDescription())
                        .icon(BitmapDescriptorFactory.defaultMarker(colorMarker)));
//                                fromResource(R.drawable.icon_rest_place)));

                builder.include(latLon);
            }
        }
        LatLngBounds bounds = builder.build();
        int padding = 100; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);
    }

    @Override
    public void onGpxParseError(String type, String message, int lineNumber, int columnNumber) {
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("An error occurred: " + message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}

