package ar.edu.itba.pam.travelapp.landing.storage;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class SharedPreferencesNightModeStorage implements NightModeStorage {
    private static final String NIGHT_MODE = "night-mode";
    private final SharedPreferences preferences;

    public SharedPreferencesNightModeStorage(final SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean isActive() {
        return preferences.getBoolean(NIGHT_MODE, false);
    }

    @Override
    public void setDefaultNightMode() {
        int mode = AppCompatDelegate.getDefaultNightMode();
        if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            preferences.edit().putBoolean(NIGHT_MODE, true).apply();
        } else {
            preferences.edit().putBoolean(NIGHT_MODE, false).apply();
        }
    }

    @Override
    public void reactivateNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}
