package com.nikpikhmanets.veloroute.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.Route.RouteData;
import com.nikpikhmanets.veloroute.Route.RouteDataAdapter;


public class RouteFragment extends Fragment {

    private TextView nameRoute;
    private ImageView imageMap;
    private String pathFile;

    public RouteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_route, container, false);

        nameRoute = (TextView) v.findViewById(R.id.nameRouteText);
        imageMap = (ImageView) v.findViewById(R.id.imageRoute);
//        pathFile = v.findViewById(R.id.);


        RouteDataAdapter adapter = new RouteDataAdapter();
        RouteData rd = adapter.getDataRoute();

        nameRoute.setText(rd.getNameRoute());
        return v;
    }
}
