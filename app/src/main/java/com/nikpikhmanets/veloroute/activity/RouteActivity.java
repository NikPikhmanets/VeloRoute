package com.nikpikhmanets.veloroute.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nikpikhmanets.veloroute.R;

public class RouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setTitle("Описание маршрута");
    }
}
