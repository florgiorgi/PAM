package ar.edu.itba.pam.travelapp.landing.storage;

public interface FtuStorage {

    boolean isActive();

    void deactivate();

    String getLang();

    void setLang(String lang);
}
