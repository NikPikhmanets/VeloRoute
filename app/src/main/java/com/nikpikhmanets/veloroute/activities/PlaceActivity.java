package com.nikpikhmanets.veloroute.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.place.Place;
import com.nikpikhmanets.veloroute.place.PlaceViewOnMapFragment;
import com.nikpikhmanets.veloroute.place.PlaceViewPagerAdapter;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    final String INTENT_PLACE = "place";

    private TextView textDescription;
    private TextView coordinate;

    private ViewPager viewPager;

    private Place localPlace;

    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private PlaceViewPagerAdapter mAdapter;

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

        ImageButton btnShowOnMaps = (ImageButton) findViewById(R.id.btnShowOnMaps);
        btnShowOnMaps.setOnClickListener(this);

        setReference();
    }

    private void initView() {

        textDescription = (TextView) findViewById(R.id.textPlaceDescription);
        coordinate = (TextView) findViewById(R.id.coordinatyText);
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
            localPlace = place;

            setTitle(place.getName());
            String coordinate = String.format("%s ° ", place.getLat()) + String.format("%s °", place.getLng());
            this.coordinate.setText(coordinate);
            textDescription.setText(place.getDescription());
        }
    }


    @Override
    public void onClick(View view) {
        final PlaceViewOnMapFragment dialog = new PlaceViewOnMapFragment();

        Bundle args = new Bundle();
        args.putString(getString(R.string.bundle_name_place), localPlace.getName());
        args.putDouble(getString(R.string.bundle_lat), localPlace.getLat());
        args.putDouble(getString(R.string.bundle_lng), localPlace.getLng());
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), localPlace.getName());
    }

    public void setReference() {
        viewPager = (ViewPager) findViewById(R.id.viewPagerPhotoPlace);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        mAdapter = new PlaceViewPagerAdapter(PlaceActivity.this, localPlace.getImageList());
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setUiPageViewController();
    }

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        viewPager.getParent().requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

