package ar.edu.itba.pam.travelapp.tripdetail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.time.format.DateTimeFormatter;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.trip.TravelMethod;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

public class EditTripDialog extends DialogFragment {

    private EditTripDialogListener listener;
    private Context context;

    private EditText from;
    private EditText to;
    private EditText destination;
    private EditText flightNumber;
    private EditText departureTime;
    private Spinner transportSpinner;
    private TravelMethod travelMethod;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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

        from = view.findViewById(R.id.from_edit);
        to = view.findViewById(R.id.to_edit);
        destination = view.findViewById(R.id.destination_edit);
        flightNumber = view.findViewById(R.id.flight_number_edit);
        departureTime = view.findViewById(R.id.departure_edit);
        transportSpinner = view.findViewById(R.id.transport_method_edit);
        transportSpinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, TravelMethod.values()));
        transportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                travelMethod = (TravelMethod) adapterView.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing :)
            }
        });



        Trip trip = listener.provideTrip();
        destination.setText(trip.getLocation());
        from.setText(trip.getFrom().format(dateFormatter));
        to.setText(trip.getTo().format(dateFormatter));
        flightNumber.setText(trip.getFlightNumber());
        departureTime.setText(trip.getDepartureTime().format(dateTimeFormatter));

        transportSpinner.setSelection(trip.getTravelMethod().ordinal());

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        try {
            listener = (EditTripDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement EditTripDialogListener");
        }
    }

    public interface EditTripDialogListener {
        void editTrip(Trip trip);
        Trip provideTrip();
    }
}


