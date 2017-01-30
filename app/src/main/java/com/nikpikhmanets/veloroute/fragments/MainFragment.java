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
import com.nikpikhmanets.veloroute.emulRoute.EmulListRoute;
import com.nikpikhmanets.veloroute.emulRoute.RouteListAdapter;

import java.util.List;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listRouteRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        List<EmulListRoute> routeList = new EmulListRoute(getContext()).getRouteList();
        RouteListAdapter adapter = new RouteListAdapter(routeList);
        rv.setAdapter(adapter);
    }
}
