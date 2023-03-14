package com.roms.go4lunch.ui.setting;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.roms.go4lunch.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}