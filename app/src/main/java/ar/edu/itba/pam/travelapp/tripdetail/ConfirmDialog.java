package ar.edu.itba.pam.travelapp.tripdetail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ConfirmDialog extends DialogFragment {

    private ConfirmDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete Trip?")
                .setPositiveButton("Confirm", (dialog, id) -> {
                    listener.confirmDelete();
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ConfirmDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ConfirmDialogListener");
        }
    }

    public interface ConfirmDialogListener {
        void confirmDelete();
    }

}
