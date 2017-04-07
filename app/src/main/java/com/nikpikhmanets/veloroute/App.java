package com.nikpikhmanets.veloroute;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.nikpikhmanets.veloroute.place.PlaceListSingle;

public class App extends Application {

    public static boolean CHECK_DATA_BASE = true;
    public static boolean FLAG_MY_LOCATION = false;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        PlaceListSingle.downloadPlaceList();
    }
}
