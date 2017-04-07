package com.nikpikhmanets.veloroute.track.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.nikpikhmanets.veloroute.track.data.TrackContract.PATH_TRACKS;

public class TrackProvider extends ContentProvider {

    private static final String TAG = TrackProvider.class.getSimpleName();

    private static final int TRACK = 100;
    private static final int TRACK_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(TrackContract.CONTENT_AUTHORITY, PATH_TRACKS, TRACK);
        sUriMatcher.addURI(TrackContract.CONTENT_AUTHORITY, PATH_TRACKS + "/#", TRACK_ID);
    }

    private TrackDbHelper mTrackHelper;

    @Override
    public boolean onCreate() {
        if (this.mTrackHelper == null) {
            this.mTrackHelper = new TrackDbHelper(getContext());
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase dataBase = mTrackHelper.getReadableDatabase();
        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case TRACK:
                cursor = dataBase.query(TrackContract.TrackEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case TRACK_ID:
                selection = TrackContract.TrackEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = dataBase.query(TrackContract.TrackEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case TRACK:
                return TrackContract.TrackEntry.CONTENT_LIST_TYPE;
            case TRACK_ID:
                return TrackContract.TrackEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TRACK:
                return insertTrack(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertTrack(Uri uri, ContentValues values) {
        String name = values.getAsString(TrackContract.TrackEntry.COLUMN_NAME);
        if(name != null){
            throw new IllegalArgumentException("Guest requires a name");
        }

        SQLiteDatabase dataBase = mTrackHelper.getWritableDatabase();
        long id = dataBase.insert(TrackContract.TrackEntry.TABLE_NAME, null, values);
        if(id == -1){
            Log.e(TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase dataBase = mTrackHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match){
            case TRACK:
                return dataBase.delete(TrackContract.TrackEntry.TABLE_NAME, selection, selectionArgs);
            case TRACK_ID:
                selection = TrackContract.TrackEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return dataBase.delete(TrackContract.TrackEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TRACK:
                return updateTrack(uri, values, selection, selectionArgs);
            case TRACK_ID:
                selection = TrackContract.TrackEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateTrack(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateTrack(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(TrackContract.TrackEntry.COLUMN_NAME)) {
            String name = values.getAsString(TrackContract.TrackEntry.COLUMN_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Guest requires a name");
            }
        }


        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mTrackHelper.getWritableDatabase();
        return database.update(TrackContract.TrackEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}
