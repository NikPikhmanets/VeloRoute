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
import com.nikpikhmanets.veloroute.interfaces.OnRecyclerItemRouteClickListener;
import com.nikpikhmanets.veloroute.interfaces.OnSortingChangeListener;
import com.nikpikhmanets.veloroute.route.RouteAdapter;
import com.nikpikhmanets.veloroute.route.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainFragment extends Fragment {

    public static final String KEY_FILTER_CHECKED_ID = "filter_checked_id";
    public static final String KEY_SORTING_CHECKED_ID = "sort_by_checked_id";
    public static final String ARG_CHECKED_ID = "checked_id";

    private final String INTENT_ROUTE = "ROUTE";

    public static final String TAG = "tag";

    private int filterCheckedId = R.id.rb_length_all;
    private int sortingCheckedId = R.id.rb_sort_by_name;

    private List<Route> routesList;
    private RouteAdapter adapter;

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
        adapter = new RouteAdapter();
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listRouteRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemRouteClickListener() {
            @Override
            public void onItemClick(Route route) {
                startRouteActivity(route);
            }
        });

        if (savedInstanceState != null) {
            filterCheckedId = savedInstanceState.getInt(KEY_FILTER_CHECKED_ID);
            sortingCheckedId = savedInstanceState.getInt(KEY_SORTING_CHECKED_ID);
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
        intent.putExtra(INTENT_ROUTE, route);
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
            case R.id.action_sort:
                showSortFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_FILTER_CHECKED_ID, filterCheckedId);
        outState.putInt(KEY_SORTING_CHECKED_ID, sortingCheckedId);
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
        arguments.putInt(ARG_CHECKED_ID, filterCheckedId);
        filterFragment.setArguments(arguments);
        filterFragment.show(getActivity().getSupportFragmentManager(), "filter");
    }

    private void showSortFragment() {
        SortFragment sortFragment = new SortFragment();
        sortFragment.setOnSortingChangeListener(new OnSortingChangeListener() {
            @Override
            public void onSortingChanged(int sortByCheckedId) {
                MainFragment.this.sortingCheckedId = sortByCheckedId;
                sortRouteList();
            }
        });
        Bundle agruments = new Bundle();
        agruments.putInt(ARG_CHECKED_ID, sortingCheckedId);
        sortFragment.setArguments(agruments);
        sortFragment.show(getActivity().getSupportFragmentManager(), "sorting");
    }

    private void filterRoutesList(){
        int minLength = 0;
        int maxLength = 1000;
        switch (filterCheckedId) {
            case R.id.rb_length_all:
                getActivity().setTitle("все маршруты");
                break;
            case R.id.rb_length_long:
                getActivity().setTitle("длинные");
                minLength = 100;
                break;
            case R.id.rb_length_middle:
                getActivity().setTitle("средние");
                minLength = 50;
                maxLength = 100;
                break;
            case R.id.rb_length_short:
                getActivity().setTitle("короткие");
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
        sortRouteList();
    }

    private void sortRouteList() {
        switch (sortingCheckedId) {
            case R.id.rb_sort_by_name:
                Collections.sort(adapter.getData(), COMPARE_BY_NAME);
                break;
            case R.id.rb_sort_by_length:
                Collections.sort(adapter.getData(), COMPARE_BY_LENGTH);
                break;
            case R.id.rb_sort_by_road:
                Collections.sort(adapter.getData(), COMPARE_BY_ROAD);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private static final Comparator<Route> COMPARE_BY_NAME = new Comparator<Route>() {
        @Override
        public int compare(Route route1, Route route2) {
            return route1.getName_ru().compareTo(route2.getName_ru());
        }
    };

    private static final Comparator<Route> COMPARE_BY_LENGTH = new Comparator<Route>() {
        @Override
        public int compare(Route route1, Route route2) {
            return route1.getLength() - route2.getLength();
        }
    };

    private static final Comparator<Route> COMPARE_BY_ROAD = new Comparator<Route>() {
        @Override
        public int compare(Route route1, Route route2) {
            return route1.getRoad() - route2.getRoad();
        }
    };

}
