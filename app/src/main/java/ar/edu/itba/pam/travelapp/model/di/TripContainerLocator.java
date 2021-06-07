package ar.edu.itba.pam.travelapp.model.di;

import android.content.Context;

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

    private static void setComponent(final TripContainer tripContainer) {
        TripContainerLocator.tripContainer = tripContainer;
    }
}
