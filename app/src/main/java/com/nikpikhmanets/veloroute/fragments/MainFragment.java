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
import com.nikpikhmanets.veloroute.activity.RouteActivity;
import com.nikpikhmanets.veloroute.adapters.OnRecyclerItemClickListener;
import com.nikpikhmanets.veloroute.adapters.RouteDataAdapter;
import com.nikpikhmanets.veloroute.models.Route;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    public static final String TAG = "tag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final List<Route> routesList = new ArrayList<>();
        final RouteDataAdapter adapter = new RouteDataAdapter();
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listRouteRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(Route route) {
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content_main, new RouteFragment())
//                        .commit();
                Intent intent = new Intent(getContext(), RouteActivity.class);
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
        switch (item.getItemId()){
            case R.id.action_filter:
                (new FilterFragment()).show(getActivity().getSupportFragmentManager(), "filter");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
