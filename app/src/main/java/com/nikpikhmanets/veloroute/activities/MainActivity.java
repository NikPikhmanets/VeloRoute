package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.fragments.MainFragment;
import com.nikpikhmanets.veloroute.fragments.MapsFragment;
import com.nikpikhmanets.veloroute.fragments.PlaceFragment;
import com.nikpikhmanets.veloroute.fragments.SettingsFragment;
import com.nikpikhmanets.veloroute.fragments.TrackFragment;
import com.nikpikhmanets.veloroute.utils.GoogleApiUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    final String RECREATION_POINTS_GPX = "recreation_points.gpx";
    final String WATER_POINTS_GPX = "water_points.gpx";
    final String TITLE_SOURCE_WATER = "Источники воды";
    final String TITLE_REST_PLACE = "Места отдыха";

    final String BUNDLE_KEY_FILE_NAME_GPX = "name_file_gpx";
    final String BUNDLE_KEY_TYPE_GPX = "type_file_gpx";
    final String BUNDLE_KEY_TITLE = "title";
    final String BUNDLE_VALUE_WAY_POINTS = "way_points";

    private MainFragment mainFragment;
    private TrackFragment trackFragment;
    private SettingsFragment settingsFragment;
    private PlaceFragment placesFragment;
    private MapsFragment mapsFragment;
//    private AboutDialogFragment aboutFragment;
    private GoogleApiClient mGoogleApiClient;

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
        View headerView = navigationView.getHeaderView(0);

        ImageView ivAvatar = (CircleImageView) headerView.findViewById(R.id.iv_avatar);
        TextView tvName = (TextView) headerView.findViewById(R.id.tv_user_name);
        Button btnSignIn = (Button) headerView.findViewById(R.id.btn_anon_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            navigationView.getMenu().findItem(R.id.nav_log_out).setVisible(!firebaseUser.isAnonymous());
            if (!firebaseUser.isAnonymous()) {
                Glide.with(this).load(firebaseUser.getPhotoUrl()).into(ivAvatar);
                tvName.setText(firebaseUser.getDisplayName());
                btnSignIn.setVisibility(View.INVISIBLE);
            } else {
                tvName.setText(R.string.title_anon);
                ivAvatar.setImageResource(R.drawable.ic_anonymous);
                btnSignIn.setVisibility(View.VISIBLE);
            }
        }

        createFragments();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_main, mainFragment).commit();
        }

        mGoogleApiClient = GoogleApiUtils.getGoogleApiClient(this, this);
    }

    private void createFragments() {
        mainFragment = new MainFragment();
        trackFragment = new TrackFragment();
        settingsFragment = new SettingsFragment();
        placesFragment = new PlaceFragment();
        mapsFragment = new MapsFragment();
//        aboutFragment = new AboutDialogFragment();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, placesFragment).commit();
                break;
            case R.id.nav_myRoute:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, trackFragment).commit();
                break;
//            case R.id.nav_maps:
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, mapsFragment).commit();
//                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, settingsFragment).commit();
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, aboutFragment).addToBackStack(null).commit();

                break;
            case R.id.nav_log_out:
                signOut();
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

    private void signOut() {
        FirebaseAuth.getInstance().signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
