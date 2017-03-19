package com.nikpikhmanets.veloroute.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.place.PlaceViewPagerAdapter;
import com.nikpikhmanets.veloroute.place.Place;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    final String INTENT_PLACE = "place";

    private TextView textDescription;
    private TextView coordinate;
    private ImageView imagePlace;
    private ImageButton btnShowOnMaps;

    private ViewPager viewPager;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private PlaceViewPagerAdapter mAdapter;
    private int[] mImageResources = {R.drawable.bastion_getman_01, R.drawable.bastion_getman_02,
            R.drawable.bastion_getman_03, R.drawable.bastion_getman_04, R.drawable.bastion_getman_05};

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

//        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcherPlace);
//        mImageSwitcher.setFactory(this);
//
//        Animation inAnimation = new AlphaAnimation(0, 1);
//        inAnimation.setDuration(1000);
//        Animation outAnimation = new AlphaAnimation(1, 0);
//        outAnimation.setDuration(1000);
//
//        mImageSwitcher.setInAnimation(inAnimation);
//        mImageSwitcher.setOutAnimation(outAnimation);
//
//        mImageSwitcher.setImageResource(mImageIds[0]);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        super.onCreate(savedInstanceState);

        setReference();

//        toolbar.setVisibility(View.GONE);

    }

    private void initView() {

        textDescription = (TextView) findViewById(R.id.textPlaceDescription);
        coordinate = (TextView) findViewById(R.id.coordinatyText);
//        imagePlace = (ImageView) findViewById(R.id.imagePlace);
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
            String coord = String.format("%s ° ", place.getLat()) + String.format("%s °", place.getLng());
            coordinate.setText(coord);
            textDescription.setText(place.getDescription());

//            Double lat = intent.getDoubleExtra(INTENT_LAT, 0);
//            Double lng = intent.getDoubleExtra(INTENT_LNG, 0);
//            String descr = intent.getStringExtra(INTENT_DESCRIPTION);


//            Glide.with(this).load(place.getImageList().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imagePlace);
        }
    }


    @Override
    public void onClick(View view) {


    }

    public void setReference() {
        viewPager = (ViewPager) findViewById(R.id.viewPagerPhotoPlace);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        mAdapter = new PlaceViewPagerAdapter(PlaceActivity.this, mImageResources);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
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

