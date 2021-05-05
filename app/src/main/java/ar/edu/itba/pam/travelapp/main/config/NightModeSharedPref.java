package ar.edu.itba.pam.travelapp.main.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class NightModeSharedPref {
    public static final String NIGHT_MODE = "night-mode";
    private SharedPreferences nightModeSharedPref;

    public NightModeSharedPref(Context context) {
        nightModeSharedPref = context.getSharedPreferences("travel-buddy-sp", Context.MODE_PRIVATE);
    }

    @SuppressLint("ApplySharedPref")
    public void setNightModeState(boolean state) {
        SharedPreferences.Editor editor = nightModeSharedPref.edit();
        editor.putBoolean(NIGHT_MODE, state);
        editor.commit();
    }

    public boolean loadNightModeState() {
        return nightModeSharedPref.getBoolean(NIGHT_MODE, false);
    }
}
