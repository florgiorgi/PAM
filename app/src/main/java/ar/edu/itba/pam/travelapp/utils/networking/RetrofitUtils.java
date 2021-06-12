package ar.edu.itba.pam.travelapp.utils.networking;

import java.io.IOException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import retrofit2.Response;

public class RetrofitUtils {
    public RetrofitUtils() {
        // no-op
    }

    public static <T> Single<T> performRequest(final Single<Response<T>> request) {
        return request.onErrorResumeNext(t -> Single.error(RetrofitUtils.convertException(t)))
                .map(RetrofitUtils::unwrapResponse);
    }

    private static <T> T unwrapResponse(Response<T> tResponse) throws RequestException {
        if (RetrofitUtils.isRequestFailed(tResponse)) {
            throw new RequestException(tResponse.code());
        }
        return tResponse.body();
    }

    public static Throwable convertException(final Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            return new NoConnectivityException(throwable);
        }
        if (throwable instanceof IOException) {
            return new RequestException(throwable);
        }
        if (throwable instanceof NullPointerException) {
            return new RequestException(throwable);
        }
        return throwable;
    }

    private static boolean isRequestFailed(final Response response) {
        return !response.isSuccessful() || isErrorCode(response.code());
    }

    private static boolean isErrorCode(int statusCode) {
        return statusCode >= 400 && statusCode < 600;
    }
}
