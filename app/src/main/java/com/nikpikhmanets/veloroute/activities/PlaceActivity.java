package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener {

    final String INTENT_NAME = "name";
    final String INTENT_DESCRIPTION = "description";
    final String INTENT_LAT = "lat";
    final String INTENT_LNG = "lng";

    TextView textDescription;
    ImageView imagePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
//        setTitle(getString(R.string.description_place));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            setViewDescriptionPlace(intent);
        }
    }

    private void initView() {
        textDescription = (TextView) findViewById(R.id.textPlaceDescription);
        imagePlace = (ImageView) findViewById(R.id.imagePlace);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                startActivity(new Intent(this, MainActivity.class));
               finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setViewDescriptionPlace(Intent intent) {
        if (intent != null) {
            int defaultValue = 0;

            String name = intent.getStringExtra(INTENT_NAME);
            setTitle(name);
            Double lat = intent.getDoubleExtra(INTENT_LAT, 0);
            Double lng = intent.getDoubleExtra(INTENT_LNG, 0);
            String descr = intent.getStringExtra(INTENT_DESCRIPTION);
            textDescription.setText(descr);

//            Glide.with(this).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageRoute);
        }
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(this, MapsActivity.class);
//        intent.putExtra(INTENT_GPX, gpx);
//        startActivity(intent);
    }
}
