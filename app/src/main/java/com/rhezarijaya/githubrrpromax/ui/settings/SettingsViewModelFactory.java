package com.rhezarijaya.githubrrpromax.ui.settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rhezarijaya.githubrrpromax.util.SettingPreferences;

public class SettingsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final SettingPreferences settingPreferences;

    public SettingsViewModelFactory(SettingPreferences settingPreferences) {
        this.settingPreferences = settingPreferences;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SettingsViewModel(settingPreferences);
    }
}
