package com.nikpikhmanets.veloroute.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nikpikhmanets.veloroute.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
//            }
//        }, 2000);
    }
}
