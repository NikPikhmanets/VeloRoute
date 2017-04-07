package com.nikpikhmanets.veloroute.track;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.track.data.TrackContract;

public class TrackListAdapter extends CursorAdapter {

    public TrackListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_list_track, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameMyRoute = (TextView) view.findViewById(R.id.nameMyRoute);
        TextView createDateMyRoute = (TextView) view.findViewById(R.id.createDateMyRoute);

        int nameColumnIndex = cursor.getColumnIndex(TrackContract.TrackEntry.COLUMN_NAME);
        int dateColumnIndex = cursor.getColumnIndex(TrackContract.TrackEntry.COLUMN_DATE);

        nameMyRoute.setText(cursor.getString(nameColumnIndex));
        createDateMyRoute.setText(cursor.getString(dateColumnIndex));
    }
}
