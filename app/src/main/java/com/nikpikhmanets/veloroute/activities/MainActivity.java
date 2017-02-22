package com.nikpikhmanets.veloroute.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.fragments.AboutFragment;
import com.nikpikhmanets.veloroute.fragments.FilterFragment;
import com.nikpikhmanets.veloroute.fragments.InterestingPlacesFragment;
import com.nikpikhmanets.veloroute.fragments.MainFragment;
import com.nikpikhmanets.veloroute.fragments.MapsFragment;
import com.nikpikhmanets.veloroute.fragments.MyRoutesFragment;
import com.nikpikhmanets.veloroute.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String RECREATION_POINTS_GPX = "recreation_points.gpx";
    final String WATER_POINTS_GPX = "water_points.gpx";
    final String TITLE_SOURCE_WATER = "Источники воды";
    final String TITLE_REST_PLACE = "Места отдыха";

    final String BUNDLE_KEY_FILE_NAME_GPX = "name_file_gpx";
    final String BUNDLE_KEY_TYPE_GPX = "type_file_gpx";
    final String BUNDLE_KEY_TITLE = "title";

    final String BUNDLE_VALUE_WAY_POINTS = "way_points";


    private MainFragment mainFragment;
    private FilterFragment filterFragment;
    private MyRoutesFragment myRoutesFragment;
    private SettingsFragment settingsFragment;
    private InterestingPlacesFragment intrestingPlacesFragment;
    private MapsFragment mapsFragment;
    private AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)
//                .build();
//        Fabric.with(fabric);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        createFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_main, mainFragment).commit();
        }
    }

    private void createFragment() {
        mainFragment = new MainFragment();
        filterFragment = new FilterFragment();
        myRoutesFragment = new MyRoutesFragment();
        settingsFragment = new SettingsFragment();
        intrestingPlacesFragment = new InterestingPlacesFragment();
        mapsFragment = new MapsFragment();
        aboutFragment = new AboutFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, mainFragment).commit();
                break;
            case R.id.nav_sourceWater:
                showMapsFragment(BUNDLE_VALUE_WAY_POINTS, WATER_POINTS_GPX, TITLE_SOURCE_WATER);
                break;
            case R.id.nav_weekendPlace:
                showMapsFragment(BUNDLE_VALUE_WAY_POINTS, RECREATION_POINTS_GPX, TITLE_REST_PLACE);
                break;
            case R.id.nav_places:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, intrestingPlacesFragment).commit();
                break;
            case R.id.nav_myRoute:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myRoutesFragment).commit();
                break;
            case R.id.nav_maps:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, mapsFragment).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, settingsFragment).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, aboutFragment).addToBackStack(null).commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showMapsFragment(String keyType, String keyFileName, String keyTitle) {
        if (mapsFragment.isAdded()) {
            mapsFragment = null;
            mapsFragment = new MapsFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TYPE_GPX, keyType);
        bundle.putString(BUNDLE_KEY_FILE_NAME_GPX, keyFileName);
        bundle.putString(BUNDLE_KEY_TITLE, keyTitle);
        mapsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, mapsFragment).commit();
    }
}
