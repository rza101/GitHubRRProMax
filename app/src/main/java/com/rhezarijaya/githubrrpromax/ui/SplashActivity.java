package com.rhezarijaya.githubrrpromax.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;

import com.rhezarijaya.githubrrpromax.R;
import com.rhezarijaya.githubrrpromax.ui.main.MainActivity;
import com.rhezarijaya.githubrrpromax.ui.settings.SettingsViewModel;
import com.rhezarijaya.githubrrpromax.ui.settings.SettingsViewModelFactory;
import com.rhezarijaya.githubrrpromax.util.Constants;
import com.rhezarijaya.githubrrpromax.util.SettingPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(getApplicationContext(), Constants.SETTINGS_NAME).build();
        SettingPreferences settingPreferences = SettingPreferences.getInstance(dataStore);

        SettingsViewModel settingsViewModel = new ViewModelProvider(
                this,
                (ViewModelProvider.Factory) new SettingsViewModelFactory(settingPreferences)
        ).get(SettingsViewModel.class);

        settingsViewModel.getAppTheme().observe(this, isDarkMode -> {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        new Handler().postDelayed(() -> {
            // setDefaultNightMode menyebabkan recreate activity, sehingga pakai flag singleTop agar tidak ada 2 instance
            startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }, Constants.SPLASH_SCREEN_DELAY);
    }
}