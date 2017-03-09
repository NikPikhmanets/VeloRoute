package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.fragments.SignInFragment;

public class AuthActivity extends AppCompatActivity {

    private static final String SIGN_IN_FRAGMENT_TAG = "sign_in_fragment";

    private SignInFragment signInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        if (savedInstanceState == null) {
            signInFragment = new SignInFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_auth, signInFragment, SIGN_IN_FRAGMENT_TAG)
                    .commit();
        } else {
            signInFragment = (SignInFragment) getSupportFragmentManager().findFragmentByTag(SIGN_IN_FRAGMENT_TAG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SignInFragment.REQUEST_GOOGLE_SIGN_IN && resultCode == RESULT_OK) {
            signInFragment.onGoogleSignInResult(data);
        }
    }
}
