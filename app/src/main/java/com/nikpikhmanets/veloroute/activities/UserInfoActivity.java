package com.nikpikhmanets.veloroute.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.utils.FirebaseUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        CircleImageView ivAvatar = (CircleImageView) findViewById(R.id.iv_user_info_avatar);
        TextView tvName = (TextView) findViewById(R.id.tv_user_info_name);
        TextView tvEmail = (TextView) findViewById(R.id.tv_user_info_email);

        FirebaseUser user = FirebaseUtils.getCurrentFirebaseUser();

        Glide.with(this).load(user.getPhotoUrl()).into(ivAvatar);

        String format = getResources().getString(R.string.text_name) + "%s";
        tvName.setText(String.format(format, user.getDisplayName()));
        format = getResources().getString(R.string.text_email) + "%s";
        tvEmail.setText(String.format(format, user.getEmail()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
