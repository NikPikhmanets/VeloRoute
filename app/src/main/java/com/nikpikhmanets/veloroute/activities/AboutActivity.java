package com.nikpikhmanets.veloroute.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nikpikhmanets.veloroute.R;

public class AboutActivity extends AppCompatActivity {

    public static final String DIALOG = "fragment_about_dialog";

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fm = getSupportFragmentManager();
        AboutDialogFragment aboutDialogFragment = (AboutDialogFragment) fm.findFragmentByTag(DIALOG);
        if (aboutDialogFragment == null) {
            aboutDialogFragment = new AboutDialogFragment();
            aboutDialogFragment.setListener(this);
            aboutDialogFragment.show(fm, DIALOG);
        }
    }

    public void onDismiss(AboutDialogFragment aboutDialogFragment){
        finish();
    }

    public static class AboutDialogFragment extends AppCompatDialogFragment {

        AboutActivity listener;
        public void setListener(AboutActivity listener){
            this.listener = listener;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_about, container, false);

            TextView version = (TextView) view.findViewById(R.id.text_version);
            version.setText(R.string.version);

            TextView about = (TextView) view.findViewById(R.id.text_about);
            about.setText(R.string.text_about);

            Button btn = (Button) view.findViewById(R.id.button_close_about);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return view;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (listener != null) {
                listener.onDismiss(this);
            }
        }
    }
}
