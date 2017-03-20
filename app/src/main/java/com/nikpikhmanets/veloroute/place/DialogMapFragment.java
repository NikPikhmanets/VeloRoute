package com.nikpikhmanets.veloroute.place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nikpikhmanets.veloroute.R;

public class DialogMapFragment extends DialogFragment implements View.OnClickListener, OnMapReadyCallback {
    private SupportMapFragment fragment;

    private double lat;
    private double lng;
    private String namePlace;

    public DialogMapFragment() {
        fragment = new SupportMapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        namePlace = getArguments().getString(getString(R.string.bundle_name_place));
        lat = getArguments().getDouble(getString(R.string.bundle_lat));
        lng = getArguments().getDouble(getString(R.string.bundle_lng));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_map, container, false);
        getDialog().setTitle(namePlace);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.mapView, fragment).commit();

        fragment.getMapAsync(this);

        Button closeBtn = (Button) view.findViewById(R.id.closeMapDialogFragmentButton);
        closeBtn.setOnClickListener(this);
        return view;
    }


    public SupportMapFragment getFragment() {
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12);
        googleMap.animateCamera(cu);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}

