package com.nikpikhmanets.veloroute;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.nikpikhmanets.veloroute.fragments.AboutFragment;
import com.nikpikhmanets.veloroute.fragments.FilterFragment;
import com.nikpikhmanets.veloroute.fragments.GoogleMapsFragment;
import com.nikpikhmanets.veloroute.fragments.IntrestingPlacesFragment;
import com.nikpikhmanets.veloroute.fragments.MainFragment;
import com.nikpikhmanets.veloroute.fragments.MyRoutesFragment;
import com.nikpikhmanets.veloroute.fragments.SettingsFragment;
import com.nikpikhmanets.veloroute.fragments.WaterSourcesFragment;

import static com.nikpikhmanets.veloroute.R.menu.maps_menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private MainFragment mainFragment;
    private FilterFragment filterFragment;
    private MyRoutesFragment myRoutesFragment;
    private SettingsFragment settingsFragment;
    private IntrestingPlacesFragment intrestingPlacesFragment;
    private WaterSourcesFragment waterSourcesFragment;
    private GoogleMapsFragment googleMapsFragment;
    private AboutFragment aboutFragment;

    boolean boolMaps; // temp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mainFragment = new MainFragment();
        filterFragment = new FilterFragment();
        myRoutesFragment = new MyRoutesFragment();
        settingsFragment = new SettingsFragment();
        waterSourcesFragment = new WaterSourcesFragment();
        intrestingPlacesFragment = new IntrestingPlacesFragment();
        googleMapsFragment = new GoogleMapsFragment();
        aboutFragment = new AboutFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_main, mainFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (boolMaps) {
            getMenuInflater().inflate(maps_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.no_map:
                googleMapsFragment.SetTypeGoogleMap(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.normal_map:
                googleMapsFragment.SetTypeGoogleMap(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite_map:
                googleMapsFragment.SetTypeGoogleMap(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.terrain_map:
                googleMapsFragment.SetTypeGoogleMap(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.hybrid_map:
                googleMapsFragment.SetTypeGoogleMap(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public boolean onNavigationItemSelected(MenuItem item) {

        boolMaps = false;

        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, mainFragment).commit();
                break;
            case R.id.nav_sourceWater:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, waterSourcesFragment).commit();
                break;
            case R.id.nav_places:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, intrestingPlacesFragment).commit();
                break;
            case R.id.nav_myRoute:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myRoutesFragment).commit();
                break;
            case R.id.nav_maps:
                boolMaps = true;
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, googleMapsFragment).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, settingsFragment).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, aboutFragment).addToBackStack(null).commit();
                break;

        }
        invalidateOptionsMenu();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
