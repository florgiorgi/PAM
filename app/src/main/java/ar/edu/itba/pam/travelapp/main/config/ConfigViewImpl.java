package ar.edu.itba.pam.travelapp.main.config;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;

import ar.edu.itba.pam.travelapp.R;


public class ConfigViewImpl extends LinearLayout implements ConfigView {
    private boolean nightModeToggled;
    private SwitchMaterial nightModeSwitch;

    public ConfigViewImpl(Context context) {
        this(context, null);
        nightModeToggled = false;
    }

    public ConfigViewImpl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        nightModeToggled = false;
    }

    public ConfigViewImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.activity_config, this);
        setOrientation(VERTICAL);

        nightModeToggled = false;
    }

    @Override
    public void bind() {
        setUpNightModeSwitch();
    }

    @Override
    public void handleNightModeSwitch(int mode) {
        nightModeToggled = true;
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    @Override
    public boolean wasNightModeToggled() {
        return nightModeToggled;
    }

    private void setUpNightModeSwitch() {
        int currentNightModeSetting = AppCompatDelegate.getDefaultNightMode();
        nightModeSwitch = findViewById(R.id.switch_night_mode);
        nightModeSwitch.setChecked(false);
        if (currentNightModeSetting == AppCompatDelegate.MODE_NIGHT_YES) {
            nightModeSwitch.setChecked(true);
        }
//        nightModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
////                handleNightModeSwitch(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
////                handleNightModeSwitch(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//        });
    }

//    if (nightModeSharedPref.loadNightModeState()) {
//        navView.setSelectedItemId(R.id.config_tab);
//        flipper.setDisplayedChild(CONFIG);
//        floatingButtonCreate.setVisibility(View.GONE);
//        nightModeSharedPref.setNightModeState(false);
//    }
}
