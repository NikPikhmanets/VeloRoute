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
import com.nikpikhmanets.veloroute.interfaces.OnFilterChange;

public class FilterFragment extends DialogFragment {

    public static final int FILTER_ROUTE_LENGTH_ALL = 0;
    public static final int FILTER_ROUTE_LENGTH_SHORT = 1;
    public static final int FILTER_ROUTE_LENGTH_MIDDLE = 2;
    public static final int FILTER_ROUTE_LENGTH_LONG = 3;

    private OnFilterChange listener;

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

        final RadioGroup rgLength = (RadioGroup)view.findViewById(R.id.rg_length);
//        RadioGroup rgRoad = (RadioGroup)view.findViewById(R.id.rg_road);
//        rgRoad.check(R.id.rb_all);
        rgLength.check(R.id.rb_length_all);
        rgLength.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int filterMode = 0;
                switch (checkedId) {
                    case R.id.rb_length_all:
                        filterMode = FILTER_ROUTE_LENGTH_ALL;
                        break;
                    case R.id.rb_length_long:
                        filterMode = FILTER_ROUTE_LENGTH_LONG;
                        break;
                    case R.id.rb_length_middle:
                        filterMode = FILTER_ROUTE_LENGTH_MIDDLE;
                        break;
                    case R.id.rb_length_short:
                        filterMode = FILTER_ROUTE_LENGTH_SHORT;
                        break;
                }

                if (listener != null) {
                    listener.onFilterChanged(filterMode);
                }

            }
        });

        view.findViewById(R.id.btn_filter_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOnFilterChangeListener(OnFilterChange listener) {
        this.listener = listener;
    }

}
