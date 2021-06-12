package ar.edu.itba.pam.travelapp.di.tripdetail;

import android.content.Context;

import androidx.annotation.VisibleForTesting;

public class DetailsContainerLocator {
    private static DetailsContainer detailsContainer;

    private DetailsContainerLocator() {
        // no-op
    }

    public static DetailsContainer locateComponent(final Context context) {
        if (detailsContainer == null) {
            setComponent(new ProductionDetailsContainer(context));
        }
        return detailsContainer;
    }

    @VisibleForTesting
    private static void setComponent(final DetailsContainer detailsContainer) {
        DetailsContainerLocator.detailsContainer = detailsContainer;
    }
}
