package ar.edu.itba.pam.travelapp.tripdetail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

public class EditTripDialog extends DialogFragment {

    private EditTripDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_trip_dialog, null);

        builder.setView(view)
                .setTitle("Edit Trip")
                .setPositiveButton("Confirm", (dialog, id) -> {
                    // TODO: create trip and validate errors
                    listener.editTrip(null);
                })
                .setNegativeButton("Cancel", (dialog, id) -> System.out.println("Cancelled"));

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (EditTripDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement EditTripDialogListener");
        }
    }

    public interface EditTripDialogListener {
        void editTrip(Trip trip);
    }
}


