package com.nikpikhmanets.veloroute.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.activities.MainActivity;


public class SignUpFragment extends Fragment {

    private EditText etEmail;
    private EditText etPass;
    private EditText etConfirmPass;
    private EditText etName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmail = (EditText) view.findViewById(R.id.et_sign_up_email);
        etPass = (EditText) view.findViewById(R.id.et_sign_up_pass);
        etConfirmPass = (EditText) view.findViewById(R.id.et_sign_up_confirm_pass);
        etName = (EditText) view.findViewById(R.id.et_sign_up_name);

        view.findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForNotEmpty();
            }
        });
    }

    @Override
    public void onStart() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().setTitle("Sign Up");
        super.onStart();
    }

    @Override
    public void onStop() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkForNotEmpty() {
        String eMail = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String name = etName.getText().toString().trim();
        if (!TextUtils.isEmpty(eMail)
                && !TextUtils.isEmpty(pass)
                && !TextUtils.isEmpty(name)) {
            if (pass.equals(etConfirmPass.getText().toString())) {
                signUpWithEmail(eMail, pass, name);
//                Log.d("tag", "checkForNotEmpty: " + eMail + pass + name);
            } else {
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "one ore more fields are empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUpWithEmail(String eMail, String pass, final String name) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(eMail, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("tag", "onComplete: success");
                            setUserName(name, task.getResult().getUser());
                        } else {
                            Toast.makeText(getContext(), "sign up error", Toast.LENGTH_SHORT).show();
                            Log.d("tag", "onComplete: " + task.getException().toString());
                        }
                    }
                });
    }

    private void setUserName(String name, FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "setting name error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
