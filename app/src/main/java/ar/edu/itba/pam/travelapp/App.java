package ar.edu.itba.pam.travelapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import ar.edu.itba.pam.travelapp.utils.LocaleHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        if (LocaleHelper.getLanguage(base).equalsIgnoreCase("en")) {
            super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
        } else {
            super.attachBaseContext(LocaleHelper.onAttach(base, "es"));
        }
    }

}
