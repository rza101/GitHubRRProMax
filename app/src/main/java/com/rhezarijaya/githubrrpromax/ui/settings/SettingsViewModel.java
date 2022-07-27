package com.rhezarijaya.githubrrpromax.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.rhezarijaya.githubrrpromax.util.SettingPreferences;

public class SettingsViewModel extends ViewModel {
    private final SettingPreferences settingPreferences;

    public SettingsViewModel(SettingPreferences settingPreferences) {
        this.settingPreferences = settingPreferences;
    }

    public LiveData<Boolean> getAppTheme(){
        return LiveDataReactiveStreams.fromPublisher(settingPreferences.getAppTheme());
    }

    public void setAppTheme(boolean isDarkMode){
        settingPreferences.setAppTheme(isDarkMode);
    }
}
