package com.nikpikhmanets.veloroute.waypoint;

public class WayPoint {

    private Float Lat;
    private Float Lon;
    private String Name;
    private String Description;

    public Float getLat() {
        return Lat;
    }

    public void setLat(Float lat) {
        Lat = lat;
    }

    public Float getLon() {
        return Lon;
    }

    public void setLon(Float lon) {
        Lon = lon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
