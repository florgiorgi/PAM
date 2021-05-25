package ar.edu.itba.pam.travelapp.landing.storage;

import android.content.SharedPreferences;

public class SharedPreferencesFTUStorage implements FtuStorage {

    private static final String FTU = "ftu";
    private final SharedPreferences preferences;

    public SharedPreferencesFTUStorage(final SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean isActive() {
        return preferences.getBoolean(FTU, true);
    }

    @Override
    public void deactivate() {
        preferences.edit().putBoolean(FTU, false).apply();
    }
}
