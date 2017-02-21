package com.nikpikhmanets.veloroute.route;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nikpikhmanets.veloroute.activities.MapsActivity;
import com.nikpikhmanets.veloroute.gpx.data.GPXDocument;
import com.nikpikhmanets.veloroute.gpx.xml.GpxParser;

import java.io.IOException;
import java.io.InputStream;

public class BuildRoute implements GpxParser.GpxParserListener  {

    private Context context;
    private GoogleMap map;
    private ProgressDialog mProgressDialog = null;
    private PolylineOptions rectOptions = new PolylineOptions();

    public BuildRoute(Context context) {
        this.context = context;
    }

    public void parseGpxFile(String gpxData) {

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
}
