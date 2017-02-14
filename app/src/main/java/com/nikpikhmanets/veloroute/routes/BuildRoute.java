package com.nikpikhmanets.veloroute.routes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nikpikhmanets.veloroute.activities.MapsActivity;
import com.nikpikhmanets.veloroute.gpx.data.GPXDocument;
import com.nikpikhmanets.veloroute.gpx.data.GPXRoute;
import com.nikpikhmanets.veloroute.gpx.data.GPXRoutePoint;
import com.nikpikhmanets.veloroute.gpx.data.GPXSegment;
import com.nikpikhmanets.veloroute.gpx.data.GPXTrack;
import com.nikpikhmanets.veloroute.gpx.data.GPXTrackPoint;
import com.nikpikhmanets.veloroute.gpx.data.GPXWayPoint;
import com.nikpikhmanets.veloroute.gpx.xml.GpxParser;
import com.nikpikhmanets.veloroute.gpx.xml.GpxParserHandler;

import java.io.IOException;
import java.io.InputStream;

public class BuildRoute implements GpxParser.GpxParserListener, GpxParserHandler.GpxParserProgressListener {

    private Context context;
    private ProgressDialog mProgressDialog = null;
    private PolylineOptions rectOptions = new PolylineOptions();

    public BuildRoute(Context context) {
        this.context = context;
    }

    public void parseGpxFile(String file) {

        AssetManager am = context.getAssets();
        try {
            InputStream input = am.open(file);
            new GpxParser(input, this, this).parse();
        } catch (IOException e) {
            Toast.makeText(context, "Файл маршрута недоступен", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGpxParseStarted() {
        mProgressDialog = ProgressDialog.show(context, "Parsing GPX", "Started");
    }

    @Override
    public void onGpxParseCompleted(GPXDocument document) {
        mProgressDialog.dismiss();

        for (int x = 0; x < document.getTracks().size(); x++) {
            for (int y = 0; y < document.getTracks().get(x).getSegments().size(); y++) {
                for (int z = 0; z < document.getTracks().get(x).getSegments().get(y).getTrackPoints().size(); z++) {
                    rectOptions.add(
                            new LatLng(
                                    document.getTracks().get(x).getSegments().get(y).getTrackPoints().get(z).getLatitude(),
                                    document.getTracks().get(x).getSegments().get(y).getTrackPoints().get(z).getLongitude()
                            )
                    );
                }
            }
        }

        Intent intent = new Intent(context, MapsActivity.class);
        MapsActivity.polylineOptions = rectOptions;
        MapsActivity.mDocument = document;
        context.startActivity(intent);
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

    @Override
    public void onGpxNewTrackParsed(int count, GPXTrack track) {
        mProgressDialog.setMessage("Finished parsing track " + track.getName());
    }

    @Override
    public void onGpxNewRouteParsed(int count, GPXRoute track) {
        mProgressDialog.setMessage("Finished parsing route " + track.getName());
    }

    @Override
    public void onGpxNewSegmentParsed(int count, GPXSegment segment) {
        mProgressDialog.setMessage("Parsing track segment " + count);
    }

    @Override
    public void onGpxNewTrackPointParsed(int count, GPXTrackPoint trackPoint) {

    }

    @Override
    public void onGpxNewRoutePointParsed(int count, GPXRoutePoint routePoint) {

    }

    @Override
    public void onGpxNewWayPointParsed(int count, GPXWayPoint wayPoint) {

    }
}
