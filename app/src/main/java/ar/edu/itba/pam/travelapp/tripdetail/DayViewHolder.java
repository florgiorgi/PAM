package ar.edu.itba.pam.travelapp.tripdetail;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.activity.Activity;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class DayViewHolder extends RecyclerView.ViewHolder {

    public View view;
    public View titleView;
    public ImageButton addButton;

    private LinearLayout activityList;
    private LinearLayout activityAndButtonList;
    private View divider;
    private ImageView arrow;
    private EditText editText;
    private Button confirm;
    private Button delete;
    private Button cancel;
    private LinearLayout buttons;

    private ActivityEventListener listener;

    /*
    *
    *  Para editar o borrar trips tenes que agregarle un listener a los botones
    *  para que llamen a los metodos "onDeleteActivity(Activity activity)" y "onEditActivity(Activity activity, String name)"
    *  del listener de arriba (ActivityEventListener)
    *
    * */

    public DayViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        //activityAndButtonList = view.findViewById(R.id.list_of_activities_and_button);
        activityList = view.findViewById(R.id.list_of_activities);
        titleView = view.findViewById(R.id.day_card_title);
        addButton = (ImageButton) view.findViewById(R.id.add_button);
        editText = view.findViewById(R.id.enter_new_activity);
        confirm = view.findViewById(R.id.confirm);
        cancel = view.findViewById(R.id.cancel);
        buttons = view.findViewById(R.id.new_activity_buttons);
        setUpClickOnCardToExpand();
    }

    public void setOnClickListener(ActivityEventListener listener) {
        this.listener = listener;
    }

    private void setUpActivities(List<Activity> activities) {
        activityList.removeAllViews();
        for (Activity a : activities) {
            TextView textView = new TextView(view.getContext());
            EditText editText = new EditText(view.getContext());
            ImageButton cancelButton = new ImageButton(view.getContext());
            ImageButton confirmButton = new ImageButton(view.getContext());
            LinearLayout editAndCancel = new LinearLayout(view.getContext());

            textView.setText(a.getName());
            editText.setText(a.getName());
            cancelButton.setBackgroundResource(R.drawable.ic_delete);
            confirmButton.setBackgroundResource(R.drawable.check_icon);
            editText.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
            editAndCancel.setVisibility(View.GONE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.6f
            );
            LinearLayout.LayoutParams confirmButtonParams = new LinearLayout.LayoutParams(
                    60,
                    60
            );
            LinearLayout.LayoutParams cancelButtonParams = new LinearLayout.LayoutParams(
                    70,
                    70
            );
            LinearLayout.LayoutParams editAndCancelParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    150
            );
            params.setMargins(2, 4, 2, 4);
            editParams.setMargins(0,0,0,0);
            confirmButtonParams.setMargins(20,50,20,0);
            cancelButtonParams.setMargins(20,42,15,0);
            editAndCancelParams.setMargins(0,0,0,0);

            textView.setLayoutParams(params);
            editText.setLayoutParams(editParams);
            cancelButton.setLayoutParams(cancelButtonParams);
            confirmButton.setLayoutParams(confirmButtonParams);
            editAndCancel.setLayoutParams(editAndCancelParams);

            editAndCancel.setOrientation(LinearLayout.HORIZONTAL);
            editAndCancel.addView(editText);
            editAndCancel.addView(confirmButton);
            editAndCancel.addView(cancelButton);

            textView.setTextSize(1, 16);
            editText.setTextSize(1,16);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setMaxLines(1);
            editText.setImeOptions(EditorInfo.IME_ACTION_SEND);

            textView.setOnClickListener(v -> {
                textView.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                editAndCancel.setVisibility(View.VISIBLE);
            });
            confirmButton.setOnClickListener(v1 -> {
                //TODO edit activity
                listener.onEditActivity(a,editText.getText().toString());
                editText.setText(a.getName());
                textView.setVisibility(View.VISIBLE);
                editText.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                editAndCancel.setVisibility(View.GONE);
                System.out.println("edited");
            });
            cancelButton.setOnClickListener(v2 -> {
                //TODO delete activity
                listener.onDeleteActivity(a);
                editText.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                editAndCancel.setVisibility(View.GONE);
                System.out.println("deleted");
            });
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        //TODO edit activity
                        listener.onEditActivity(a,editText.getText().toString());
                        editText.setText(a.getName());
                        textView.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);
                        confirmButton.setVisibility(View.GONE);
                        cancelButton.setVisibility(View.GONE);
                        editAndCancel.setVisibility(View.GONE);
                        System.out.println("edited");
                        return true;
                    }
                    return false;
                }
            });
            activityList.addView(textView);
            activityList.addView(editAndCancel);
        }
    }

    public void bind(final List<Activity> activities, final int position, LocalDate date) {
        final TextView dayNum = itemView.findViewById(R.id.day_number);
        dayNum.setText("Day " + (position + 1));
        setUpActivities(activities);
        setUpAddButton(date);
    }

    private void setUpClickOnCardToExpand() {
        titleView.setOnClickListener(v -> {
            divider = view.findViewById(R.id.divider);
            arrow = view.findViewById(R.id.arrow_down);

            if (divider.getVisibility() == View.GONE) {
                divider.setVisibility(View.VISIBLE);
                activityList.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.arrow_down);
            } else {
                divider.setVisibility(View.GONE);
                activityList.setVisibility(View.GONE);
                addButton.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                buttons.setVisibility(View.GONE);
                editText.setText("");
                editText.setHintTextColor(Color.GRAY);
                arrow.setImageResource(R.drawable.arrow_up);
            }
        });
    }

    private void setUpAddButton(LocalDate date) {

        addButton.setOnClickListener(v -> {
            addButton.setVisibility(View.GONE);
            EditText editText = view.findViewById(R.id.enter_new_activity);
            Button confirm = view.findViewById(R.id.confirm);
            Button cancel = view.findViewById(R.id.cancel);
            LinearLayout buttons = view.findViewById(R.id.new_activity_buttons);
            buttons.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
            confirm.setOnClickListener(v1 -> {
                String text = editText.getText().toString();
                if (!text.equals("")) {
                    listener.onClickNewActivity(text, date);
                    buttons.setVisibility(View.GONE);
                    editText.setText("");
                    editText.setHintTextColor(Color.GRAY);
                    editText.setVisibility(View.GONE);
                    addButton.setVisibility(View.VISIBLE);
                } else {
                    editText.setHintTextColor(Color.RED);
                }
            });

            cancel.setOnClickListener(v12 -> {
                buttons.setVisibility(View.GONE);
                editText.setText("");
                editText.setHintTextColor(Color.GRAY);
                editText.setVisibility(View.GONE);
                addButton.setVisibility(View.VISIBLE);
            });
        });
    }

}