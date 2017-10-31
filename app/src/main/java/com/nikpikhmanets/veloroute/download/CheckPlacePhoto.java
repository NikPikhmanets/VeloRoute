package com.nikpikhmanets.veloroute.download;

import com.nikpikhmanets.veloroute.place.Place;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class CheckPlacePhoto {

    private List<Place> placeList;
    private List<String> listMissingFile = new ArrayList<>();

    CheckPlacePhoto() {
    }

    void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    List<String> checkPhoto(String pathFilePhoto) {
        File photo = new File(pathFilePhoto);
        if (photo.exists()) {
            for (int i = 0; i < placeList.size(); i++) {
                for (int y = 0; y < placeList.get(i).getImageList().size(); y++) {
                    String filePath = pathFilePhoto + placeList.get(i).getImageList().get(y);
                    photo = new File(filePath);
                    if (!(photo.exists() && photo.isFile())) {
                        listMissingFile.add("image_place/" + placeList.get(i).getImageList().get(y));
                    }
                }
            }
        }
        return listMissingFile;
    }
}
