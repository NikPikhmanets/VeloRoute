package com.nikpikhmanets.veloroute.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.track.TrackList;
import com.nikpikhmanets.veloroute.track.TrackListAdapter;

import java.util.List;

public class MyRoutesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_routes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listMyRoute);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        List trackList = new TrackList(this.getContext()).getListTrack();
        TrackListAdapter adapter = new TrackListAdapter(trackList);
        rv.setAdapter(adapter);
    }
}
