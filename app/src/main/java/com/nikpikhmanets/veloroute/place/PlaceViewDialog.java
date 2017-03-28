package com.nikpikhmanets.veloroute.place;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nikpikhmanets.veloroute.R;

import java.io.File;

public class PlaceViewDialog extends AppCompatActivity {

    private String description;
    private String img;
    public static final String DIALOG_PLACE = "fragment_dialog_place";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDescription(getIntent().getStringExtra("txt"));
        setImg(getIntent().getStringExtra("img"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fm = getSupportFragmentManager();
        PlaceDialogFragment placeDialogFragment = (PlaceDialogFragment) fm.findFragmentByTag(DIALOG_PLACE);
        if (placeDialogFragment == null) {

            placeDialogFragment = new PlaceDialogFragment();
            placeDialogFragment.setListener(this);

            Bundle args = new Bundle();
            args.putString("txt", description);
            args.putString("img", img);
            placeDialogFragment.setArguments(args);;
            placeDialogFragment.show(fm, DIALOG_PLACE);
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void onDismiss(PlaceDialogFragment placeDialogFragment) {
        finish();
    }

    public static class PlaceDialogFragment extends AppCompatDialogFragment {

        PlaceViewDialog listener;

        public void setListener(PlaceViewDialog listener) {
            this.listener = listener;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_place, container, false);

            String description = getArguments().getString("txt");
            String img = getArguments().getString("img");

            TextView txt = (TextView) view.findViewById(R.id.textDialogPlace);
            if(txt != null)
                txt.setText(description);

            ImageView imgView = (ImageView) view.findViewById(R.id.imgDialogPlace);
            File file;
            if (img != null) {
                file = new File(getContext().getApplicationInfo().dataDir + "/image_place/", img);
                Glide.with(getContext()).load(file).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgView);
            }

            Button btn = (Button) view.findViewById(R.id.btnCloseDlgPlace);
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
