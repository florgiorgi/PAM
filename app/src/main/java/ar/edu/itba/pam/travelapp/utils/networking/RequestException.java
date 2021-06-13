package ar.edu.itba.pam.travelapp.utils.networking;

public class RequestException extends Exception {
    public RequestException(int code) {
        super("Request unsuccessful. Response returned status code " + code);
    }

    public RequestException(Throwable throwable) {
        super(throwable);
    }
}
