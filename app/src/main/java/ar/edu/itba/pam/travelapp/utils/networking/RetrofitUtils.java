package ar.edu.itba.pam.travelapp.utils.networking;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Response;

public class RetrofitUtils {
    public RetrofitUtils() {
        // no-op
    }

    public static <T> Single<T> performRequest(final Single<Response<T>> request) {
        return request.onErrorResumeNext(t -> Single.error(RetrofitUtils.convertException(t)))
                .map(RetrofitUtils::unwrapResponse);
    }

    public static <T> Flowable<List<T>> performRequest(final Flowable<Response<List<T>>> request) {
        return request.onErrorResumeNext(
                (Function<? super Throwable, ? extends Publisher<? extends Response<List<T>>>>)
                t -> Flowable.error(RetrofitUtils.convertException(t)))
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

    private static <T> boolean isRequestFailed(final Response<T> response) {
        return !response.isSuccessful() || isErrorCode(response.code());
    }

    private static boolean isErrorCode(int statusCode) {
        return statusCode >= 400 && statusCode < 600;
    }
}
