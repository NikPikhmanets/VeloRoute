package com.nikpikhmanets.veloroute.place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

public class PlaceViewDialog extends DialogFragment {

    private String title;
    private String description;
    private String img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getArguments().getString("title"));
        setDescription(getArguments().getString("txt"));
        setImg(getArguments().getString("img"));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_place, container, false);
        getDialog().setTitle(title);

        TextView txt = (TextView) view.findViewById(R.id.textDialogPlace);
        if (txt != null)
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
}
