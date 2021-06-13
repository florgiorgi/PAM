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
    }

    public ConfigViewImpl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConfigViewImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_config, this);
        setOrientation(VERTICAL);
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

    @Override
    public void setUpNightModeSwitch() {
        nightModeToggled = false;
        nightModeSwitch = findViewById(R.id.switch_night_mode);
        nightModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                handleNightModeSwitch(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                handleNightModeSwitch(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }
}
