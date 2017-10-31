package com.nikpikhmanets.veloroute.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nikpikhmanets.veloroute.R;

/**
 * Created by Ivan on 09-Mar-17.
 */

abstract public class GoogleApiUtils {

    public static GoogleApiClient getGoogleApiClient(Context context, GoogleApiClient.OnConnectionFailedListener listener) {
        return new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, listener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, getGsio(context))
                .build();

    }

    public static GoogleSignInOptions getGsio(Context context) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

}
