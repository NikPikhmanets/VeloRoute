package com.nikpikhmanets.veloroute.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.track.TrackListAdapter;

public class TrackFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

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

        listView.setEmptyView(view.findViewById(R.id.textViewEmpty));

        TrackListAdapter adapter = new TrackListAdapter(getContext(), null, 0);
        listView.setAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
