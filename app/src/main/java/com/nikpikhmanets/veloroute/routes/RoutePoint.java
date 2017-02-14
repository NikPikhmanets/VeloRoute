package com.nikpikhmanets.veloroute.routes;

public class RoutePoint {

    private Float Latitude;
    private Float Longitude;
    private Double Elevation;
//    private DateTime Time;

    public Float getLatitude() {
        return Latitude;
    }

    public void setLatitude(Float latitude) {
        Latitude = latitude;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }

    public Double getElevation() {
        return Elevation;
    }

    public void setElevation(Double elevation) {
        Elevation = elevation;
    }

//    public DateTime getTime() {
//        return Time;
//    }
//
//    public void setTime(DateTime time) {
//        Time = time;
//    }
}
