package com.nikpikhmanets.veloroute.utils;

import com.google.android.gms.maps.GoogleMap;

public class GoogleMapsUtils {

    public static void setTypeMaps(String mapStyle, GoogleMap googleMap) {
        switch (mapStyle) {
            case "no_map":
                googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case "normal":
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case "terrain":
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case "satellite":
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case "hybrid":
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
        }
    }
}
