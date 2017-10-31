package com.nikpikhmanets.veloroute.place;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Place implements Parcelable {

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
    private String name;
    private String description;
    private Double lat;
    private Double lng;
    private List<String> imageList;
    private List<Uri> imageListUri;

    public Place() {

    }

    private Place(Parcel in) {
        name = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        description = in.readString();
        imageList = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(description);
        dest.writeStringList(imageList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<Uri> getImageListUri() {
        return imageListUri;
    }

    public void setImageListUri(List<Uri> imageListUri) {
        this.imageListUri = imageListUri;
    }
}
