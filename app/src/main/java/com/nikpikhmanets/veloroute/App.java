package com.nikpikhmanets.veloroute;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.nikpikhmanets.veloroute.place.Place;
import com.nikpikhmanets.veloroute.route.Route;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    public List<Route> routeList = new ArrayList<>();
    public List<Place> placeList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public List<Place> getPlaceList() {
        return placeList;
    }
}
