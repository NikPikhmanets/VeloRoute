package com.nikpikhmanets.veloroute.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.Exclude;

import java.util.List;

public class Route implements Parcelable {

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
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
    private List<String> listPlace;

    public Route() {
    }

    public Route(int length, int road, String name_en, String name_ru, String name_ua, String image, String description, String gpx, String imageURL, List<String> listPlace) {
        this.length = length;
        this.road = road;
        this.name_en = name_en;
        this.name_ru = name_ru;
        this.name_ua = name_ua;
        this.description = description;
        this.gpx = gpx;
        this.imageURL = imageURL;
        this.listPlace = listPlace;
        Log.d(TAG, "name: " + name_ru + "; length: " + length + "; road: " + road + "; image: " + image);
    }

    protected Route(Parcel in) {
        length = in.readInt();
        road = in.readInt();
        name_en = in.readString();
        name_ru = in.readString();
        name_ua = in.readString();
        image = in.readString();
        description = in.readString();
        gpx = in.readString();
        imageURL = in.readString();
        listPlace = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(length);
        dest.writeInt(road);
        dest.writeString(name_en);
        dest.writeString(name_ru);
        dest.writeString(name_ua);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(gpx);
        dest.writeString(imageURL);
        dest.writeStringList(listPlace);
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public void setName_ua(String name_ua) {
        this.name_ua = name_ua;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName_ru() {
        return name_ru;
    }

    public void setName_ru(String name_ru) {
        this.name_ru = name_ru;
    }

    public int getRoad() {
        return road;
    }

    public void setRoad(int road) {
        this.road = road;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGpx() {
        return gpx;
    }

    public void setGpx(String gpx) {
        this.gpx = gpx;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<String> getListPlace() {
        return listPlace;
    }

    public void setListPlace(List<String> listPlace) {
        this.listPlace = listPlace;
    }

    @Exclude
    public int getDirt() {
        return 100 - road;
    }
}
