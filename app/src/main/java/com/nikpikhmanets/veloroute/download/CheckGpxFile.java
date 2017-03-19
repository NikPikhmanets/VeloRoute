package com.nikpikhmanets.veloroute.download;

import com.nikpikhmanets.veloroute.route.Route;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class CheckGpxFile {

    private List<Route> routeList;
    private List<String> listMissingFile = new ArrayList<>();

    CheckGpxFile() {
    }

    void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    List<String> checkGpxFile(String pathFileGpx) {
        File fileGpx = new File(pathFileGpx);
        if (fileGpx.exists()) {
            for (int i = 0; i < routeList.size(); i++) {
                String filePath = pathFileGpx + routeList.get(i).getGpx() + ".gpx";
                fileGpx = new File(filePath);
                if (!(fileGpx.exists() && fileGpx.isFile())) {
                    listMissingFile.add("gpx_file/" + routeList.get(i).getGpx() + ".gpx");
                }
            }
        }
        return listMissingFile;
    }
}
