package ar.edu.itba.pam.travelapp.landing.storage;

public interface NightModeStorage {
    boolean isActive();

    void setDefaultNightMode();

    void reactivateNightMode();

    void detachFromView(int mode);
}
