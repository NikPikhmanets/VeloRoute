package com.nikpikhmanets.veloroute.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.Route.RouteData;
import com.nikpikhmanets.veloroute.Route.RouteDataAdapter;

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

        List<RouteData> routeList = new RouteData(getContext()).getRouteList();
        RouteDataAdapter adapter = new RouteDataAdapter(routeList, getContext());
        rv.setAdapter(adapter);
    }

    @Override
    public void onStart() {
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
