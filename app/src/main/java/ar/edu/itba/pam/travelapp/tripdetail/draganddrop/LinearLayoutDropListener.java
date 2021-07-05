package ar.edu.itba.pam.travelapp.tripdetail.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.time.LocalDate;

import ar.edu.itba.pam.travelapp.tripdetail.DayViewHolder;

public class LinearLayoutDropListener implements View.OnDragListener {
    private final WeakReference<DayViewHolder> view;

    public LinearLayoutDropListener(DayViewHolder dayViewHolder) {
        this.view = new WeakReference<>(dayViewHolder);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        final int action = event.getAction();

        switch(action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();
                    // returns true to indicate that the View can accept the dragged data
                    return true;
                }
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
            case DragEvent.ACTION_DRAG_EXITED:
                // Invalidate the view to force a redraw in the new tint
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;

            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
                if (view.get() != null) {
                    String[] dataPackage = dragData.split(",");
                    Toast.makeText(view.get().getView().getContext(), "Dragged data is " + dragData, Toast.LENGTH_LONG).show();
                    view.get().getListener().onMoveActivity(LocalDate.parse(dataPackage[0]), Long.parseLong(dataPackage[1]), view.get().getDate());
                }

                // Invalidates the view to force a redraw
                v.invalidate();

                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                // Invalidates the view to force a redraw
                v.invalidate();
                if (event.getResult()) {
                    if (view.get() != null) {
                        Toast.makeText(view.get().getView().getContext(), "The drop was handled.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (view.get() != null) {
                        Toast.makeText(view.get().getView().getContext(), "The drop didn't work.", Toast.LENGTH_LONG).show();
                    }
                }
                return true;

            default:
                System.out.println("Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }
}
