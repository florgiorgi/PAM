package ar.edu.itba.pam.travelapp.di.newtrip;

import android.content.Context;

import androidx.annotation.VisibleForTesting;

public class NewTripContainerLocator {
    private static NewTripContainer newTripContainer;

    public NewTripContainerLocator() {
        // no-op
    }

    public static NewTripContainer locateComponent(final Context context) {
        if (newTripContainer == null) {
            setComponent(new ProductionNewTripContainer(context));
        }
        return newTripContainer;
    }

    @VisibleForTesting
    private static void setComponent(final NewTripContainer newTripContainer) {
        NewTripContainerLocator.newTripContainer = newTripContainer;
    }
}
