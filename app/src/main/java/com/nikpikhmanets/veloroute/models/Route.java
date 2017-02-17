package com.nikpikhmanets.veloroute.models;

import android.util.Log;

import com.google.firebase.database.Exclude;


/**
 * Created by Ivan on 06.02.2017.
 */

public class Route {

    private static final String TAG = "tag";

    private int length;
    private int road;
    private String name_en;
    private String name_ru;
    private String name_ua;
    private String image;
    private String description;
    private String gpx;
    private String imageURL;

    public Route() {
    }

    public Route(int length, int road, String name_en, String name_ru, String name_ua, String image, String description, String gpx, String imageURL) {
        this.length = length;
        this.road = road;
        this.name_en = name_en;
        this.name_ru = name_ru;
        this.name_ua = name_ua;
        this.description = description;
        this.gpx = gpx;
        this.imageURL = imageURL;
        Log.d(TAG, "name: " + name_ru + "; length: " + length + "; road: " + road + "; image: " + image);
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public void setName_ru(String name_ru) {
        this.name_ru = name_ru;
    }

    public void setName_ua(String name_ua) {
        this.name_ua = name_ua;
    }

    public void setRoad(int road) {
        this.road = road;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGpx(String gpx) {
        this.gpx = gpx;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getLength() {
        return length;
    }

    public String getName_ru() {
        return name_ru;
    }

    public int getRoad() {
        return road;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getGpx() {
        return gpx;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Exclude
    public int getDirt() {
        return 100 - road;
    }
}
