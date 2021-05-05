package ar.edu.itba.pam.travelapp.main.details;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.pam.travelapp.R;
import ar.edu.itba.pam.travelapp.model.activity.Activity;
import ar.edu.itba.pam.travelapp.model.trip.Trip;

public class DayViewHolder extends RecyclerView.ViewHolder {

    public View view;
    public View titleView;
    public ImageButton addButton;
    private DetailsActivity context;
    private LinearLayout activityList;
    private LinearLayout activityAndButtonList;
    private View divider;
    private ImageView arrow;
    private EditText editText;
    private Button confirm;
    private Button cancel;
    private LinearLayout buttons;

    public DayViewHolder(@NonNull View itemView, DetailsActivity context) {
        super(itemView);
        this.view = itemView;
        this.context = context;
        activityAndButtonList = view.findViewById(R.id.list_of_activities_and_button);
        activityList = view.findViewById(R.id.list_of_activities);
        titleView = view.findViewById(R.id.day_card_title);
        addButton = (ImageButton) view.findViewById(R.id.add_button);
        editText = view.findViewById(R.id.enter_new_activity);
        confirm = view.findViewById(R.id.confirm);
        cancel = view.findViewById(R.id.cancel);
        buttons = view.findViewById(R.id.new_activity_buttons);

        setUpClickOnCardToExpand();
        //setUpAddButton();
    }

    private void setUpActivities(List<Activity> activities) {

        for (Activity a : activities) {
            TextView textView = new TextView(context);
            textView.setText(a.getName());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.setMargins(2, 4, 2, 4);
            textView.setLayoutParams(params);
            textView.setTextSize(1, 16);
            activityList.addView(textView);
        }
    }

    public void bind(final List<Activity> activities, final int position, LocalDate date, Trip trip) {
        final TextView day_num = itemView.findViewById(R.id.day_number);
        //day_num.setText("Day " + position + " " + date);
        day_num.setText("Day " + position);

        setUpActivities(activities);
        setUpAddButton(date, trip);
    }

    private void setUpClickOnCardToExpand() {

        titleView.setOnClickListener(v -> {
            divider = view.findViewById(R.id.divider);
            arrow = view.findViewById(R.id.arrow_down);

            if (divider.getVisibility() == View.GONE) {
                divider.setVisibility(View.VISIBLE);
                activityAndButtonList.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.arrow_down);
            } else {
                divider.setVisibility(View.GONE);
                activityAndButtonList.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.arrow_up);
            }
        });
    }

    private void setUpAddButton(LocalDate date, Trip trip) {

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.GONE);

                EditText editText = view.findViewById(R.id.enter_new_activity);
                Button confirm = view.findViewById(R.id.confirm);
                Button cancel = view.findViewById(R.id.cancel);
                LinearLayout buttons = view.findViewById(R.id.new_activity_buttons);

                buttons.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);

                //aca se tendria que guardar la activity en la bd
                //el LocalDate correspondiente a la activity esta guardado en la variable date
                confirm.setOnClickListener(v1 -> {
                    String text = editText.getText().toString();
                    if (!text.equals("")) {
                        addActivity(text);

                        context.createActivity(text, date);

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
            }

            private void addActivity(String newActivity) {

                TextView textView = new TextView(context);
                textView.setText(newActivity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                params.setMargins(2, 2, 2, 2);
                textView.setLayoutParams(params);
                textView.setTextSize(1, 15);
                activityList.addView(textView);
            }
        });
    }
}