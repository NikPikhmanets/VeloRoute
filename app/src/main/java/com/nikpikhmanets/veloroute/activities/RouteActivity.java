package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;

public class RouteActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nameRoute;
    TextView lengthRoute;
    TextView roadRoute;
    TextView roadRouteLabel;
    TextView tvRouteDescription;
    ImageView imageRoute;

    Button showOnMapsBtn;

    String gpxFile;

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

//            //костыль
            String mDrawableName = intent.getStringExtra("image");

            // костыль, проверка построения маршрута
            if (mDrawableName.equals("image_route_budische")) {
                gpxFile = "budyshche.gpx";
            }
            if (mDrawableName.equals("image_route_buky")) {
                gpxFile = "buky.gpx";
            }
            if (mDrawableName.equals("image_route_chygyryn_subotiv")) {
                gpxFile = "chygyryn_subotiv.gpx";
            }
            if (mDrawableName.equals("image_route_kam_canyon")) {
                gpxFile = "kamyansky_canyon.gpx";
            }
            if (mDrawableName.equals("image_route_kaniv")) {
                gpxFile = "kaniv.gpx";
            }
            if (mDrawableName.equals("image_route_malo_smila_karyer")) {
                gpxFile = "malosmilyansky_career.gpx";
            }
            if (mDrawableName.equals("image_route_orbita")) {
                gpxFile = "orbita.gpx";
            }
            if (mDrawableName.equals("image_route_sofiin_stovp")) {
                gpxFile = "sofiin_stovp.gpx";
            }
            if (mDrawableName.equals("image_route_zyvun")) {
                gpxFile = "zhyvun.gpx";
            }
            if (mDrawableName.equals("image_route_pavkin_krug")) {
                gpxFile = "pavkin_krug.gpx";
            }
            if (mDrawableName.equals("image_route_starosilya")) {
                gpxFile = "starosilya.gpx";
            }
            if (mDrawableName.equals("image_route_svydivok")) {
                gpxFile = "svydivok.gpx";
            }
            if (mDrawableName.equals("image_route_vasylkivska_gatj")) {
                gpxFile = "vasylkivska_gatj.gpx";
            }
            if (mDrawableName.equals("image_route_vitryak_monastyr_irdin")) {
                gpxFile = "vitryak_vinograd_monastyr_irdin.gpx";
            }
            if (mDrawableName.equals("image_route_xy_dub")) {
                gpxFile = "xy_dub.gpx";
            }
            if (mDrawableName.equals("image_route_xy_ground")) {
                gpxFile = "xy_ground.gpx";
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("gpx", gpxFile);
        startActivity(intent);
    }
}
