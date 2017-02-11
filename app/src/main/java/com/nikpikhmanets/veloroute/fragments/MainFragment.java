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
import com.nikpikhmanets.veloroute.models.Route;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {


    public static final String TAG = "tag";

    private int filterMode;
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

        routesList = new ArrayList<>();
        adapter = new RouteDataAdapter();
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listRouteRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(Route route) {
                Intent intent = new Intent(getContext(), RouteActivity.class);
                intent.putExtra("name", route.getName_ru());
                intent.putExtra("length", route.getLength());
                intent.putExtra("road", route.getRoad());
                intent.putExtra("image", route.getImage());
                intent.putExtra("dirt", route.getDirt());
                intent.putExtra("description", route.getDescription());
                startActivity(intent);
            }
        });

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
                adapter.setData(routesList);
                view.findViewById(R.id.pb_loading).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: ");
            }
        });

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

    private void showFilterFragment() {
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setOnFilterChangeListener(new OnFilterChange() {
            @Override
            public void onFilterChanged(int filterMode) {
                MainFragment.this.filterMode = filterMode;
                filterRoutes();
            }
        });
        filterFragment.show(getActivity().getSupportFragmentManager(), "filter");
    }

    private void filterRoutes(){
        int minLength = 0;
        int maxLength = 1000;
        switch (filterMode) {
            case FilterFragment.FILTER_ROUTE_LENGTH_ALL:
                break;
            case FilterFragment.FILTER_ROUTE_LENGTH_LONG:
                minLength = 100;
                break;
            case FilterFragment.FILTER_ROUTE_LENGTH_MIDDLE:
                minLength = 50;
                maxLength = 100;
                break;
            case FilterFragment.FILTER_ROUTE_LENGTH_SHORT:
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

}
