package com.nikpikhmanets.veloroute.interfaces;

import android.location.Location;
import android.net.Uri;

public interface IGPSLoggerServiceRemote {
    long getTrackId();
    int loggingState();
    boolean isMediaPrepared();
    Uri storeMediaUri(Uri mediaUri);
    Uri storeMetaData(String key, String value);
    Location getLastWaypoint();
    float getTrackedDistance();
}
