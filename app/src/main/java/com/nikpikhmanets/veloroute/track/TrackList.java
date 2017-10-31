package com.nikpikhmanets.veloroute.track;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackList {

    List<TrackList> listTrack;

    private int id;
    private String name;
    private String createDate;
    private Uri uriTrack;

    private Context cntx;

    public TrackList(Context cntx) {
        this.cntx = cntx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Uri getUriTrack() {
        return uriTrack;
    }

    public void setUriTrack(Uri uriTrack) {
        this.uriTrack = uriTrack;
    }

    public List getListTrack() {

        listTrack = new ArrayList<>();
        String[] listFileGpx = new String[0];

        AssetManager myAssetManager = cntx.getApplicationContext().getAssets();
        try {
            listFileGpx = myAssetManager.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int id = 0;
        for (String aListFileGpx : listFileGpx) {
            TrackList track = new TrackList(cntx);
            track.setId(id++);
            track.setName(aListFileGpx);
//            track.setCreateDate();
//            track.setUriTrack();
            listTrack.add(track);
        }
        return listTrack;
    }
}
