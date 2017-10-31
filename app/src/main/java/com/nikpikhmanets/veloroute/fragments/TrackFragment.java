package com.nikpikhmanets.veloroute.fragments;


import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.track.TrackListAdapter;
import com.nikpikhmanets.veloroute.track.data.TrackContract;

public class TrackFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int TRACK_LOADER_LOADER = 0;

    TrackListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_routes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.menu_my_track));

        ListView listView = (ListView) view.findViewById(R.id.listTrack);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri currentGuestUri = ContentUris.withAppendedId(TrackContract.TrackEntry.CONTENT_URI, id);
            }
        });
        listView.setEmptyView(view.findViewById(R.id.textViewEmpty));

        adapter = new TrackListAdapter(getContext(), null, 0);
        listView.setAdapter(adapter);
        getLoaderManager().initLoader(TRACK_LOADER_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                TrackContract.TrackEntry._ID,
                TrackContract.TrackEntry.COLUMN_NAME,
                TrackContract.TrackEntry.COLUMN_DATE};

        // Загрузчик запускает запрос ContentProvider в фоновом потоке
        return new CursorLoader(getContext(),
                TrackContract.TrackEntry.CONTENT_URI,   // URI контент-провайдера для запроса
                projection,             // колонки, которые попадут в результирующий курсор
                null,                   // без условия WHERE
                null,                   // без аргументов
                null);                  // сортировка по умолчанию
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
