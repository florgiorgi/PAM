package ar.edu.itba.pam.travelapp.main.details;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.pam.travelapp.R;

public class DayViewHolder extends RecyclerView.ViewHolder {

    public View view;
    public View titleView;
    public ImageButton addButton;
    private Context context;
    private LinearLayout activityList;
    private LinearLayout activityAndButtonList;
    private View divider;
    private ImageView arrow;
    private EditText editText;
    private Button confirm;
    private Button cancel;
    private LinearLayout buttons;

    public DayViewHolder(@NonNull View itemView, Context context) {
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
        setUpActivities();
        setUpAddButton();
    }

    private void setUpActivities() {
        List<String> names = new ArrayList<>();

        names.add("Go to moma");
        names.add("Go to met");
        names.add("Chill with friends");

        for (String s : names) {
            TextView textView = new TextView(context);
            textView.setText(s);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.setMargins(2, 2, 2, 2);
            textView.setLayoutParams(params);
            textView.setTextSize(1, 15);
            activityList.addView(textView);
        }
    }

    public void bind(final String text) {
        final TextView textView = itemView.findViewById(R.id.day_number);

        textView.setText(text);
    }

    private void setUpClickOnCardToExpand() {

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    private void setUpAddButton() {

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

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = editText.getText().toString();
                        if (!text.equals("")) {
                            addActivity(text);

                            buttons.setVisibility(View.GONE);
                            editText.setText("");
                            editText.setHintTextColor(Color.GRAY);
                            editText.setVisibility(View.GONE);
                            addButton.setVisibility(View.VISIBLE);
                        } else {
                            editText.setHintTextColor(Color.RED);
                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        buttons.setVisibility(View.GONE);
                        editText.setText("");
                        editText.setHintTextColor(Color.GRAY);
                        editText.setVisibility(View.GONE);
                        addButton.setVisibility(View.VISIBLE);
                    }
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