package com.nikpikhmanets.veloroute.route;

import org.joda.time.DateTime;

public class RoutePoint {

    private Double Latitude;
    private Double Longitude;
    private Double Elevation;
    private DateTime Time;

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getElevation() {
        return Elevation;
    }

    public void setElevation(Double elevation) {
        Elevation = elevation;
    }

    public DateTime getTime() {
        return Time;
    }

    public void setTime(DateTime time) {
        Time = time;
    }
}
