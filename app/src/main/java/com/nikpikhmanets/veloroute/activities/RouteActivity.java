package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nikpikhmanets.veloroute.R;

public class RouteActivity extends AppCompatActivity implements View.OnClickListener {

    final String INTENT_NAME = "name";
    final String INTENT_LENGTH = "length";
    final String INTENT_ROAD = "road";
    final String INTENT_IMAGE = "image";
    final String INTENT_DIRT = "dirt";
    final String INTENT_DESCRIPTION = "description";
    final String INTENT_GPX = "gpx";

    final String GROUND = "Грунт:";
    final String ASPHALT = "Асфальт:";
    final String GROUND_ASPHALT = "Грунт/асфальт:";

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
        setTitle(getString(R.string.description_route));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            gpx = intent.getStringExtra(INTENT_GPX);
            setViewDescriptionRoute(intent);
        }
    }

    private void initView() {
        nameRoute = (TextView) findViewById(R.id.nameRouteLabel);
        roadRouteLabel = (TextView) findViewById(R.id.groundRouteLabel);
        lengthRoute = (TextView) findViewById(R.id.distance);
        roadRoute = (TextView) findViewById(R.id.roadRoute);
        imageRoute = (ImageView) findViewById(R.id.imageRoute);
        tvRouteDescription = (TextView) findViewById(R.id.tv_description);

        showOnMapsBtn = (Button) findViewById(R.id.showOnMaps);
        showOnMapsBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setViewDescriptionRoute(Intent intent) {
        if (intent != null) {
            int defaultValue = 0;

            String name = intent.getStringExtra(INTENT_NAME);
            String image = intent.getStringExtra(INTENT_IMAGE);
            String description = intent.getStringExtra(INTENT_DESCRIPTION);
            int length = intent.getIntExtra(INTENT_LENGTH, defaultValue);
            int road = intent.getIntExtra(INTENT_ROAD, defaultValue);
            int dirt = intent.getIntExtra(INTENT_DIRT, defaultValue);

            nameRoute.setText(name);

            StorageReference imageRouteFB = FirebaseStorage.getInstance().getReference("routes_images/" + image + ".jpg");
            Glide.with(getApplicationContext())
                    .using(new FirebaseImageLoader())
                    .load(imageRouteFB)
                    .into(imageRoute);

            tvRouteDescription.setText(description);

            lengthRoute.setText(String.format("%s км", length));
            if (road == 0) {
                roadRouteLabel.setText(GROUND);
                roadRoute.setText(String.format("%s", dirt));
            } else if (dirt == 0) {
                roadRouteLabel.setText(ASPHALT);
                roadRoute.setText(String.format("%s", road));
            } else {
                roadRouteLabel.setText(GROUND_ASPHALT);
                roadRoute.setText(String.format("%s / %s", dirt, road));
            }
        }

        RelativeLayout Rl = (RelativeLayout) findViewById(R.id.headLayout);
        int i = Rl.getHeight();
//        ViewGroup.LayoutParams params = Rl.getLayoutParams();
//
//        ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(imageRoute.getLayoutParams());
//        mlp.setMargins(0, params.height, 0, 0);
//        imageRoute.setLayoutParams(mlp);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(INTENT_GPX, gpx);
        startActivity(intent);
    }
}
