package com.nikpikhmanets.veloroute.track.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.nikpikhmanets.veloroute.track.data.TrackContract.*;

public class TrackDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = TrackDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "track.db";
    private static final int DATABASE_VERSION = 1;

    public TrackDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TRACK_TABLE = "CREATE TABLE " + TrackEntry.TABLE_NAME + " ("
                + TrackEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TrackEntry.COLUMN_NAME + " TEXT NOT NULL DEFAULT 0, "
                + TrackEntry.COLUMN_LAT + " REAL NOT NULL DEFAULT 0, "
                + TrackEntry.COLUMN_LON + " REAL NOT NULL DEFAULT 0, "
                + TrackEntry.COLUMN_SPEED + " REAL NOT NULL DEFAULT 0, "
                + TrackEntry.COLUMN_HEIGHT + " REAL NOT NULL DEFAULT 0);";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
