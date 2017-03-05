package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.route.Route;

public class RouteActivity extends AppCompatActivity implements View.OnClickListener {

    final String GROUND = "Грунт:";
    final String ASPHALT = "Асфальт:";
    final String GROUND_ASPHALT = "Грунт/асфальт:";

    private final String INTENT_ROUTE = "ROUTE";

    TextView nameRoute;
    TextView lengthRoute;
    TextView roadRoute;
    TextView roadRouteLabel;
    TextView tvRouteDescription;
    ImageView imageRoute;

    Button showOnMapsBtn;

    private Route route;

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
        route = getIntent().getParcelableExtra(INTENT_ROUTE);
        setViewDescriptionRoute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        RelativeLayout Rl = (RelativeLayout) findViewById(R.id.headLayout);
        Rl.measure(0, 0);
        int i = Rl.getMeasuredHeight();

        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(imageRoute.getLayoutParams());
        marginParams.setMargins(0, i, 0, 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginParams);
        imageRoute.setLayoutParams(layoutParams);
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

    private void setViewDescriptionRoute() {
        initView();

        if (route != null) {
            nameRoute.setText(route.getName_ru());

            Glide.with(this).load(route.getImageURL()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageRoute);

            tvRouteDescription.setText(route.getDescription());

            lengthRoute.setText(String.format("%s км", route.getLength()));
            if (route.getRoad() == 0) {
                roadRouteLabel.setText(GROUND);
                roadRoute.setText(String.format("%s", route.getDirt()));
            } else if (route.getDirt() == 0) {
                roadRouteLabel.setText(ASPHALT);
                roadRoute.setText(String.format("%s", route.getRoad()));
            } else {
                roadRouteLabel.setText(GROUND_ASPHALT);
                roadRoute.setText(String.format("%s / %s", route.getDirt(), route.getRoad()));
            }
        } else {
            Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(INTENT_ROUTE, route);
        startActivity(intent);
    }
}
