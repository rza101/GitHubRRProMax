package com.rhezarijaya.githubrrpromax.util;

import android.annotation.SuppressLint;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@SuppressLint("UnsafeOptInUsageWarning")
public class SettingPreferences {
    private static volatile SettingPreferences INSTANCE;
    private final RxDataStore<Preferences> rxDataStore;

    public SettingPreferences(RxDataStore<Preferences> rxDataStore) {
        this.rxDataStore = rxDataStore;
    }

    public static SettingPreferences getInstance(RxDataStore<Preferences> dataStore) {
        if (INSTANCE == null) {
            synchronized (SettingPreferences.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SettingPreferences(dataStore);
                }
            }
        }

        return INSTANCE;
    }

    public Flowable<Boolean> getAppTheme(){
        return rxDataStore.
                data().
                map(preferences -> preferences.get(Constants.APP_THEME_KEY) != null ? preferences.get(Constants.APP_THEME_KEY) : false);
    }

    public void setAppTheme(boolean isDarkMode){
        rxDataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(Constants.APP_THEME_KEY, isDarkMode);

            return Single.just(mutablePreferences);
        });
    }
}
