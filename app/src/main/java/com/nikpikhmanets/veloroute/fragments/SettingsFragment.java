package com.nikpikhmanets.veloroute.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.nikpikhmanets.veloroute.R;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

//    private ListPreference language;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.menu_settings));

        View root = view.getRootView();

        // Set the color
        root.setBackgroundColor(Color.LTGRAY);

//        bindPreferenceSummaryToValue(findPreference(getString(R.string.default_map_style)));
//        bindPreferenceSummaryToValue(findPreference(getString(R.string.width_line)));

//        language = (ListPreference) getPreferenceManager().findPreference("list1");
//        language.setOnPreferenceChangeListener(this);
//        language.setSummary(language.getValue());
    }

    private void bindPreferenceSummaryToValue(Preference preference) {

//        if (preference instanceof ListPreference) {
//            // For list preferences, look up the correct display value in
//            // the preference's 'entries' list.
//            ListPreference listPreference = (ListPreference) preference;
//            int index = listPreference.findIndexOfValue(stringValue);
//
//            // Set the summary to reflect the new value.
//            preference.setSummary(
//                    index >= 0
//                            ? listPreference.getEntries()[index]
//                            : null);
//
//        } else {
//            // For all other preferences, set the summary to the value's
//            // simple string representation.
//            preference.setSummary(stringValue);
//        }
//        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        preference.setSummary((String) newValue);
        return false;
    }
}
