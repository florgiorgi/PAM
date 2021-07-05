package ar.edu.itba.pam.travelapp.tripdetail.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.View;
import android.widget.TextView;

import ar.edu.itba.pam.travelapp.model.activity.Activity;


public class DayActivityDragOnLongClickListener implements View.OnLongClickListener {
    private final Activity a;
    private final TextView textView;

    public DayActivityDragOnLongClickListener(Activity a, TextView textView) {
        this.a = a;
        this.textView = textView;
    }

    @Override
    public boolean onLongClick(View v) {
        String data = a.getDate() + "," + a.getId();
        // Create a new ClipData.Item from the ImageView object's tag
        ClipData.Item item = new ClipData.Item(data);
        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        ClipData dragData = new ClipData(
                data,
                new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                item);
        View.DragShadowBuilder activityShadow = new View.DragShadowBuilder(textView);
        v.startDragAndDrop(dragData,  // the data to be dragged
                activityShadow,  // the drag shadow builder
                null,      // no need to use local data
                0          // flags (not currently used, set to 0)
        );
        return false;
    }
}
