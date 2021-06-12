package ar.edu.itba.pam.travelapp.di.main;

import android.content.Context;

import androidx.annotation.VisibleForTesting;

public class TripContainerLocator {
    private static TripContainer tripContainer;

    private TripContainerLocator() {
        // no-op
    }

    public static TripContainer locateComponent(final Context context) {
        if (tripContainer == null) {
            setComponent(new ProductionTripContainer(context));
        }
        return tripContainer;
    }

    @VisibleForTesting
    private static void setComponent(final TripContainer tripContainer) {
        TripContainerLocator.tripContainer = tripContainer;
    }
}
