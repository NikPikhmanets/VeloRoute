package com.nikpikhmanets.veloroute.place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Николай on 05.03.2017.
 */

public class PlaceListSingle {

    private static List<Place> instance;

    public static List<Place> getInstance(){
        if(instance == null){
            instance = new ArrayList<>();
        }
        return instance;
    }

}
