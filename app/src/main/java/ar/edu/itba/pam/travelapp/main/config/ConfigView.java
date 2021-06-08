package ar.edu.itba.pam.travelapp.main.config;

public interface ConfigView {
    void setUpNightModeSwitch();

    void handleNightModeSwitch(int mode);

    boolean wasNightModeToggled();
}
