package com.nikpikhmanets.veloroute.fragments;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.interfaces.OnSortingChangeListener;

public class SortFragment extends DialogFragment {

    private OnSortingChangeListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sort, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle(R.string.title_sort);
        RadioGroup rgSortBy = (RadioGroup) view.findViewById(R.id.rg_sort);
        rgSortBy.check(getArguments().getInt(MainFragment.ARG_CHECKED_ID));
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (listener != null) {
                    listener.onSortingChanged(checkedId);
                }
            }
        });
        view.findViewById(R.id.btn_sorting_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOnSortingChangeListener(OnSortingChangeListener listener) {
        this.listener = listener;
    }

}
