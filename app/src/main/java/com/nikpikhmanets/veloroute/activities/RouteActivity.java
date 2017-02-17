package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.routes.BuildRoute;

public class RouteActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nameRoute;
    TextView lengthRoute;
    TextView roadRoute;
    TextView roadRouteLabel;
    TextView tvRouteDescription;
    ImageView imageRoute;

    Button showOnMapsBtn;

    String gpx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setTitle("Описание маршрута");

        nameRoute = (TextView) findViewById(R.id.nameRouteLabel);
        roadRouteLabel = (TextView) findViewById(R.id.groundRouteLabel);
        lengthRoute = (TextView) findViewById(R.id.distance);
        roadRoute = (TextView) findViewById(R.id.roadRoute);
        imageRoute = (ImageView) findViewById(R.id.imageRoute);
        tvRouteDescription = (TextView) findViewById(R.id.tv_description);

        showOnMapsBtn = (Button) findViewById(R.id.showOnMaps);
        showOnMapsBtn.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            gpx = intent.getStringExtra("gpx");
            setViewDescriptionRoute(intent);
        }
    }

    private void setViewDescriptionRoute(Intent intent) {
        if (intent != null) {
            int defaultValue = 0;

            String name = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");
            int length = intent.getIntExtra("length", defaultValue);
            int road = intent.getIntExtra("road", defaultValue);
            int dirt = intent.getIntExtra("dirt", defaultValue);

            tvRouteDescription.setText(description);

            nameRoute.setText(name);
            lengthRoute.setText(String.format("%s км", length));
            if (road == 0) {
                roadRouteLabel.setText("Грунт:");
                roadRoute.setText(String.format("%s", dirt));
            } else if (dirt == 0) {
                roadRouteLabel.setText("Асфальт:");
                roadRoute.setText(String.format("%s", road));
            } else {
                roadRouteLabel.setText("Грунт/асфальт:");
                roadRoute.setText(String.format("%s / %s", dirt, road));
            }
        }
    }

    @Override
    public void onClick(View view) {

        StorageReference gpxReference = FirebaseStorage.getInstance().getReference("gpx_file/" + gpx + ".gpx");
        gpxReference.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Log.d("tag", "onSuccess: ");
                BuildRoute br = new BuildRoute(RouteActivity.this);
                br.parseGpxFile(bytes);
            }
        });

    }
}
