package com.nikpikhmanets.veloroute.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.activities.MainActivity;
import com.nikpikhmanets.veloroute.utils.DialogUtils;
import com.nikpikhmanets.veloroute.utils.GoogleApiUtils;

public class SignInFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    public static final int REQUEST_GOOGLE_SIGN_IN = 101;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private EditText etEmail;
    private EditText etPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = (EditText) view.findViewById(R.id.et_login);
        etPass = (EditText) view.findViewById(R.id.et_pass);

        view.findViewById(R.id.btn_google_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                getActivity().startActivityForResult(signInIntent, REQUEST_GOOGLE_SIGN_IN);
            }
        });

        view.findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                if (!TextUtils.isEmpty(eMail) && !TextUtils.isEmpty(pass)) {
                    signInWithEmail(eMail, pass);
                } else {
                    Toast.makeText(getContext(), R.string.msg_empty_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_auth, new SignUpFragment())
                        .addToBackStack("1")
                        .commit();
            }
        });

        view.findViewById(R.id.btn_anonymous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAnonymously();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(R.string.title_sign_in);
        mGoogleApiClient = GoogleApiUtils.getGoogleApiClient(getActivity(), this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    public void onGoogleSignInResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            signInWithGoogle(result.getSignInAccount());
        } else {
            Toast.makeText(getContext(), R.string.msg_err_google_signin, Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        final ProgressDialog waitingDialog = DialogUtils.getWaitingDialog(getContext(), getString(R.string.msg_sign_in_g));
        waitingDialog.show();

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        waitingDialog.dismiss();
                        if (task.isSuccessful()) {
                            startMainActivity();
                        } else {
                            Toast.makeText(getContext(), R.string.msg_err_auth_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInWithEmail(String email, String pass) {
        final ProgressDialog waitingDialog = DialogUtils.getWaitingDialog(getContext(), getString(R.string.msg_sign_in_email));
        waitingDialog.show();
        mFirebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        waitingDialog.dismiss();
                        if (task.isSuccessful()) {
                            startMainActivity();
                        } else {
                            Toast.makeText(getContext(), R.string.msg_err_auth_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInAnonymously() {
        final ProgressDialog waitingDialog = DialogUtils.getWaitingDialog(getContext(), getString(R.string.msg_sign_in_anon));
        waitingDialog.show();
        mFirebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                waitingDialog.dismiss();
                if (task.isSuccessful()) {
                    startMainActivity();
                } else {
                    Toast.makeText(getContext(), R.string.msg_err_auth_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startMainActivity() {
        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
