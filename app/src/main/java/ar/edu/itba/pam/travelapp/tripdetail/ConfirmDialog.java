package ar.edu.itba.pam.travelapp.tripdetail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import ar.edu.itba.pam.travelapp.R;


public class ConfirmDialog extends DialogFragment {

    private ConfirmDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_trip)
                .setPositiveButton(R.string.confirm, (dialog, id) -> {
                    listener.confirmDelete();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
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
