package com.nikpikhmanets.veloroute.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.activities.PlaceActivity;
import com.nikpikhmanets.veloroute.interfaces.OnRecyclerItemPlaceClickListener;
import com.nikpikhmanets.veloroute.place.Place;
import com.nikpikhmanets.veloroute.place.PlaceAdapter;
import com.nikpikhmanets.veloroute.place.PlaceListSingle;

import java.util.List;

public class PlaceFragment extends Fragment {

    final String INTENT_PLACE = "place";

    List<Place> placeList;
    PlaceAdapter placeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interesting_places, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.menu_places));

        placeList = PlaceListSingle.getListPlace(); //new ArrayList<>();
        placeAdapter = new PlaceAdapter();

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.placeRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(placeAdapter);
        placeAdapter.setData(PlaceListSingle.getListPlace());

        placeAdapter.setOnItemPlaceClickListener(new OnRecyclerItemPlaceClickListener() {
            @Override
            public void onItemClick(Place place) {
                startPlaceActivity(place);
            }
        });
    }

    private void startPlaceActivity(Place place) {
        Intent intent = new Intent(getContext(), PlaceActivity.class);
        intent.putExtra(INTENT_PLACE, place);
        startActivity(intent);
    }
}
