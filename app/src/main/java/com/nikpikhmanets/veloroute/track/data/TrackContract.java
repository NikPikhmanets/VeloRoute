package com.nikpikhmanets.veloroute.track.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class TrackContract {

    // Content Authority
    public static final String CONTENT_AUTHORITY = "com.nikpikhmanets.veloroute";

    // Создаём объект Uri
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Данные. Обычно имя таблицы
    public static final String PATH_TRACKS = "track";

    public static final class TrackEntry implements BaseColumns {

        /** The content URI to access the guest data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TRACKS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRACKS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRACKS;

        public final static String TABLE_NAME = "tracks";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_LAT = "latitude";
        public final static String COLUMN_LON = "longitude";
        public final static String COLUMN_SPEED = "speed";
        public final static String COLUMN_HEIGHT = "height";
    }
}
