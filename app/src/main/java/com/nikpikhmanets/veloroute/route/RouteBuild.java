package com.nikpikhmanets.veloroute.route;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.gpx.data.GPXDocument;
import com.nikpikhmanets.veloroute.gpx.xml.GpxParser;
import com.nikpikhmanets.veloroute.place.Place;
import com.nikpikhmanets.veloroute.place.PlaceListSingle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RouteBuild implements GpxParser.GpxParserListener {

    private Context context;

    private GoogleMap map;
    private String widthLineMap;

    private ProgressDialog mProgressDialog = null;
    private PolylineOptions rectOptions = new PolylineOptions();

    private GpxParser.GpxParserListener mGpxParserListener = this;

    private List<Place> localListPlace = new ArrayList<>();

    public RouteBuild(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;
    }

    public void parseGpxFile(final Route route) {

        if (route == null) {
            Toast.makeText(context, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            return;
        }
        checkPlace(route);

        File localFile = null;
        StorageReference gpxReference = FirebaseStorage.getInstance().getReference("gpx_file/" + route.getGpx() + ".gpx");
        try {
            localFile = File.createTempFile("other", "gpx");
            localFile.deleteOnExit();

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert localFile != null;
        final File finalLocalFile = localFile;
        gpxReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                try {
                    InputStream input = new FileInputStream(finalLocalFile);
                    new GpxParser(input, mGpxParserListener, null).parse();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Err open route", Toast.LENGTH_SHORT).show();
            }
        });


//        AssetManager am = context.getAssets();
//        InputStream input = null;
//        try {
//            input = am.open(gpxData);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (input != null)
//            new GpxParser(input, this, null).parse();
//        else {
//
//            input = new ByteArrayInputStream(gpxData.getBytes());
//            if (input. != null) {
//                new GpxParser(input, this, null).parse();
//            } else
//                Toast.makeText(context, "Err open route", Toast.LENGTH_SHORT).show();
//        }
    }

    private void checkPlace(final Route route) {
        List<String> listPlaceRoute = route.getListPlace();
        List<Place> listPlace = PlaceListSingle.getListPlace();
        if(listPlaceRoute != null && listPlace != null) {

            for (int i = 0; i < listPlaceRoute.size(); i++) {
                for (int y = 0; y < listPlace.size(); y++) {
                    if (listPlace.get(y).getName().equals(listPlaceRoute.get(i))) {
                        localListPlace.add(listPlace.get(y));
                    }
                }
            }
        }
        else
            Toast.makeText(context, "Ошибка разбора данных маркеров", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGpxParseStarted() {
        mProgressDialog = ProgressDialog.show(context, "Open Route", "Started");
    }

    @Override
    public void onGpxParseCompleted(GPXDocument document) {
        mProgressDialog.dismiss();

        getSettings();

        rectOptions.color(Color.RED);

        if (!widthLineMap.isEmpty())
            rectOptions.width(Integer.parseInt(widthLineMap));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        parseRoute(document, builder);

        map.addPolyline(rectOptions);

        addMarker();

        LatLngBounds bounds = builder.build();
        int padding = 100; // offset from edges of the map in pixels

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);

        //        mMap.moveCamera(CameraUpdateFactory.zoomIn());
//        mMap.setOnMarkerClickListener(this);
    }

    private void addMarker() {
        if (localListPlace.size() != 0) {
            for (int i = 0; i < localListPlace.size(); i++) {
                LatLng latLng = new LatLng(localListPlace.get(i).getLat(), localListPlace.get(i).getLng());
                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(localListPlace.get(i).getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
        }
    }

    private void parseRoute(GPXDocument document, LatLngBounds.Builder builder) {
        for (int x = 0; x < document.getTracks().size(); x++) {
            for (int y = 0; y < document.getTracks().get(x).getSegments().size(); y++) {
                for (int z = 0; z < document.getTracks().get(x).getSegments().get(y).getTrackPoints().size(); z++) {
                    LatLng latLon = new LatLng(document.getTracks().get(x).getSegments().get(y).getTrackPoints().get(z).getLatitude(),
                            document.getTracks().get(x).getSegments().get(y).getTrackPoints().get(z).getLongitude());
                    rectOptions.add(latLon);
                    builder.include(latLon);
                }
            }
        }
    }

    private void getSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        widthLineMap = prefs.getString(context.getString(R.string.width_line), "");
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
