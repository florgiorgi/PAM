package ar.edu.itba.pam.travelapp.utils.networking;

public class NoConnectivityException extends Exception {
    public NoConnectivityException() {
        super();
    }

    public NoConnectivityException(Throwable throwable) {
        super(throwable);
    }
}
