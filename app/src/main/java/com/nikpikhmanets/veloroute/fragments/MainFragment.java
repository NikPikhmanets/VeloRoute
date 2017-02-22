package com.nikpikhmanets.veloroute.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.activities.RouteActivity;
import com.nikpikhmanets.veloroute.interfaces.OnFilterChange;
import com.nikpikhmanets.veloroute.interfaces.OnRecyclerItemClickListener;
import com.nikpikhmanets.veloroute.adapters.RouteDataAdapter;
import com.nikpikhmanets.veloroute.route.Route;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    final String INTENT_NAME = "name";
    final String INTENT_LENGTH = "length";
    final String INTENT_ROAD = "road";
    final String INTENT_IMAGE = "image";
    final String INTENT_DIRT = "dirt";
    final String INTENT_DESCRIPTION = "description";
    final String INTENT_GPX = "gpx";

    public static final String TAG = "tag";

    private int filterCheckedId = R.id.rb_length_all;
    private List<Route> routesList;
    private RouteDataAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.app_name));

        routesList = new ArrayList<>();
        adapter = new RouteDataAdapter();
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listRouteRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(Route route) {
                startRouteActivity(route);
            }
        });

        if (savedInstanceState != null) {
            filterCheckedId = savedInstanceState.getInt("filter_checked_id");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference routesReference = ref.child("routes");
        routesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                routesList.clear();
                Log.d(TAG, "onDataChange: ");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    routesList.add(snapshot.getValue(Route.class));
                }
                filterRoutesList();
                view.findViewById(R.id.pb_loading).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: ");
            }
        });

    }

    private void startRouteActivity(Route route) {
        Intent intent = new Intent(getContext(), RouteActivity.class);
        intent.putExtra(INTENT_NAME, route.getName_ru());
        intent.putExtra(INTENT_LENGTH, route.getLength());
        intent.putExtra(INTENT_ROAD, route.getRoad());
        intent.putExtra(INTENT_IMAGE, route.getImage());
        intent.putExtra(INTENT_DIRT, route.getDirt());
        intent.putExtra(INTENT_DESCRIPTION, route.getDescription());
        intent.putExtra(INTENT_GPX, route.getGpx());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        setHasOptionsMenu(true);
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                showFilterFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("filter_checked_id", filterCheckedId);
    }

    private void showFilterFragment() {
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setOnFilterChangeListener(new OnFilterChange() {
            @Override
            public void onFilterChanged(int filterCheckedId) {
                MainFragment.this.filterCheckedId = filterCheckedId;
                filterRoutesList();
            }
        });
        Bundle arguments = new Bundle();
        arguments.putInt("checked_id", filterCheckedId);
        filterFragment.setArguments(arguments);
        filterFragment.show(getActivity().getSupportFragmentManager(), "filter");
    }

    private void filterRoutesList(){
        int minLength = 0;
        int maxLength = 1000;
        switch (filterCheckedId) {
            case R.id.rb_length_all:
                break;
            case R.id.rb_length_long:
                minLength = 100;
                break;
            case R.id.rb_length_middle:
                minLength = 50;
                maxLength = 100;
                break;
            case R.id.rb_length_short:
                maxLength = 50;
                break;
        }

        List<Route> filteredList = new ArrayList<>();

        for (Route route : routesList) {
            if (route.getLength() >= minLength && route.getLength() < maxLength) {
                filteredList.add(route);
            }
        }
        adapter.setData(filteredList);
    }

    private void loadGpxFromFirebase(Route route, String gpxName) {

    }


}
