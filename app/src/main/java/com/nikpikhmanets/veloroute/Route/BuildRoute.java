package com.nikpikhmanets.veloroute.Route;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.ticofab.androidgpxparser.parser.GPXParser;
import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.Track;
import io.ticofab.androidgpxparser.parser.domain.TrackPoint;
import io.ticofab.androidgpxparser.parser.domain.TrackSegment;

public class BuildRoute implements GetPolylineOptionsRoute {

    private Context context;
    public List<RoutePoint> routeList = new ArrayList<>();

    public BuildRoute(Context context) {
        this.context = context;
    }

    private void loadGpxFile(String file) {
        GPXParser mParser;
        Gpx parsedGpx;

        AssetManager am = context.getAssets();
        try {
            InputStream inputStream = am.open(file);
            mParser = new GPXParser();
            parsedGpx = mParser.parse(inputStream);
            if(parsedGpx != null) {
                List<Track> tracks = parsedGpx.getTracks();

                for (int i = 0; i < tracks.size(); i++) {
                    Track track = tracks.get(i);
                    List<TrackSegment> segments = track.getTrackSegments();

                    for (int j = 0; j < segments.size(); j++) {
                        TrackSegment segment = segments.get(i);

                        for (TrackPoint trackPoint : segment.getTrackPoints()) {

                            RoutePoint route = new RoutePoint();
                            route.setLongitude(trackPoint.getLongitude());
                            route.setLatitude(trackPoint.getLatitude());

                            if(trackPoint.getElevation() != null) {
                                route.setElevation(trackPoint.getElevation());
                            }
                            if(trackPoint.getTime() != null) {
                                route.setTime(trackPoint.getTime());
                            }
                            routeList.add(route);
                        }
                    }
                }
            }

        } catch (IOException | XmlPullParserException e) {
            Toast.makeText(context, "Ошибка чтения файла", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public PolylineOptions getPolylineOptionsRout() {

        loadGpxFile("my_route/real_sofiin_stovp.gpx");
        PolylineOptions rectOptions = new PolylineOptions();

        if(routeList != null) {
            for (int i = 0; i < routeList.size(); i++) {
                rectOptions.add(new LatLng(routeList.get(i).getLatitude(), routeList.get(i).getLongitude()));
            }
        }
        return rectOptions;
    }
}
