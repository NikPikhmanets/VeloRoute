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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.activities.PlaceActivity;
import com.nikpikhmanets.veloroute.interfaces.OnRecyclerItemPlaceClickListener;
import com.nikpikhmanets.veloroute.place.Place;
import com.nikpikhmanets.veloroute.place.PlaceAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlaceFragment extends Fragment {

    final String INTENT_NAME = "name";
    final String INTENT_DESCRIPTION = "description";
    final String INTENT_LAT = "lat";
    final String INTENT_LNG = "lng";

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
        placeAdapter = new PlaceAdapter();
        placeList = new ArrayList<>();

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.placeRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(placeAdapter);
        placeAdapter.setOnItemPlaceClickListener(new OnRecyclerItemPlaceClickListener() {
            @Override
            public void onItemClick(Place place) {
                startPlaceActivity(place);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference routesReference = ref.child("place");
        routesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                placeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    placeList.add(snapshot.getValue(Place.class));
                }
                placeAdapter.setData(placeList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void startPlaceActivity(Place place) {
        Intent intent = new Intent(getContext(), PlaceActivity.class);
//        intent.putExtra(INTENT_NAME, place.getName());
//        intent.putExtra(INTENT_DESCRIPTION, place.getDescription());
//        intent.putExtra(INTENT_LAT, place.getLat());
//        intent.putExtra(INTENT_LNG, place.getLng());
        startActivity(intent);
    }
}