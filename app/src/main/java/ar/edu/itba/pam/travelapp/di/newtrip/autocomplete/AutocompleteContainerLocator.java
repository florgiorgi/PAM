package ar.edu.itba.pam.travelapp.di.newtrip.autocomplete;

import android.content.Context;

import androidx.annotation.VisibleForTesting;

public class AutocompleteContainerLocator {
    private static AutocompleteContainer autocompleteContainer;

    private AutocompleteContainerLocator() {
        // no-op
    }

    public static AutocompleteContainer locateComponent(final Context context) {
        if (autocompleteContainer == null) {
            setComponent(new ProductionAutocompleteContainer(context));
        }
        return autocompleteContainer;
    }

    @VisibleForTesting
    private static void setComponent(final AutocompleteContainer autocompleteContainer) {
        AutocompleteContainerLocator.autocompleteContainer = autocompleteContainer;
    }
}
