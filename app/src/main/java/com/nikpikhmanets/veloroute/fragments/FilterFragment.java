package com.nikpikhmanets.veloroute.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.nikpikhmanets.veloroute.R;

public class FilterFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setTitle("фильтр маршрутов");
        }

        view.findViewById(R.id.btn_filter_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        RadioGroup rgLength = (RadioGroup)view.findViewById(R.id.rg_length);
        RadioGroup rgRoad = (RadioGroup)view.findViewById(R.id.rg_road);
        rgRoad.check(R.id.rb_all);
        rgLength.check(R.id.rb_length_all);

    }
}
