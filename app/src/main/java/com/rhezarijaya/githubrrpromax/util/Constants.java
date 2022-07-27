package com.rhezarijaya.githubrrpromax.util;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;

public class Constants {
    // API URLS
    public static final String GITHUB_API_BASE_URL = "https://api.github.com/";

    // DATABASE
    public static final String DATABASE_NAME = "db_githubrrpromax";

    // INTENT KEYS
    public static final String INTENT_MAIN_TO_USER_DETAIL = "INTENT_MAIN_TO_USER_DETAIL";

    // MAGIC NUMBERS
    public static final long SPLASH_SCREEN_DELAY = 3000;

    // PREFERENCES SETTINGS
    public static final String SETTINGS_NAME = "githubrrpromax_settings";
    public static final Preferences.Key<Boolean> APP_THEME_KEY = PreferencesKeys.booleanKey("APP_THEME_KEY");
}
