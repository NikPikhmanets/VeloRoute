package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.place.Place;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener {

    final String INTENT_PLACE = "place";

    private TextView textDescription;
    private TextView coordinaty;
    private ImageView imagePlace;
    private ImageButton btnShowOnMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setViewDescriptionPlace((Place) getIntent().getParcelableExtra(INTENT_PLACE));

        btnShowOnMaps = (ImageButton) findViewById(R.id.btnShowOnMaps);
        btnShowOnMaps.setOnClickListener(this);
    }

    private void initView() {

        textDescription = (TextView) findViewById(R.id.textPlaceDescription);
        coordinaty = (TextView) findViewById(R.id.coordinatyText);
        imagePlace = (ImageView) findViewById(R.id.imagePlace);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setViewDescriptionPlace(Place place) {
        initView();
        if (place != null) {

            setTitle(place.getName());
            String coord = String.format("%s °", place.getLat()) + String.format("%s °", place.getLng());
            coordinaty.setText(coord);
            textDescription.setText(place.getDescription());

//            Double lat = intent.getDoubleExtra(INTENT_LAT, 0);
//            Double lng = intent.getDoubleExtra(INTENT_LNG, 0);
//            String descr = intent.getStringExtra(INTENT_DESCRIPTION);


//            Glide.with(this).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageRoute);
        }
    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
