package ar.edu.itba.pam.travelapp.main.storage;

public interface NightModeStorage {
    boolean isActive();

    void setDefaultNightMode();

    void reactivateNightMode();
}
