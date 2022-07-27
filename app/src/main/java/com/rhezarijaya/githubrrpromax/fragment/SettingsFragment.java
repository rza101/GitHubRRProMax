package com.rhezarijaya.githubrrpromax.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.rhezarijaya.githubrrpromax.R;
import com.rhezarijaya.githubrrpromax.ui.settings.SettingsViewModel;
import com.rhezarijaya.githubrrpromax.ui.settings.SettingsViewModelFactory;
import com.rhezarijaya.githubrrpromax.util.Constants;
import com.rhezarijaya.githubrrpromax.util.SettingPreferences;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SwitchPreference darkModeSwitch;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.settings);

        String darkModeSwitchKey = getResources().getString(R.string.settings_dark_mode_key);
        darkModeSwitch = findPreference(darkModeSwitchKey);

        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(requireActivity().getApplicationContext(), Constants.SETTINGS_NAME).build();
        SettingPreferences settingPreferences = SettingPreferences.getInstance(dataStore);

        SettingsViewModel settingsViewModel = new ViewModelProvider(
                requireActivity(),
                (ViewModelProvider.Factory) new SettingsViewModelFactory(settingPreferences)
        ).get(SettingsViewModel.class);

        settingsViewModel.getAppTheme().observe(requireActivity(), isDarkMode -> {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                darkModeSwitch.setChecked(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                darkModeSwitch.setChecked(false);
            }
        });

        darkModeSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                settingsViewModel.setAppTheme((Boolean) newValue);
                return true;
            }
        });
    }
}
