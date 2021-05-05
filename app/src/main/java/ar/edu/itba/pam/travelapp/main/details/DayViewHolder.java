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


    public DayViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.view = itemView;
        this.context = context;
        activityAndButtonList = view.findViewById(R.id.list_of_activities_and_button);
        activityList = view.findViewById(R.id.list_of_activities);
        titleView = view.findViewById(R.id.day_card_title);
        addButton = (ImageButton) view.findViewById(R.id.add_button);

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
                //Toast.makeText(context,"prueba",Toast.LENGTH_SHORT).show();
                addButton.setVisibility(View.GONE);

                EditText editText = new EditText(context);
                editText.setHint("Enter new activity");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                params.setMargins(2, 2, 2, 2);
                editText.setLayoutParams(params);
                editText.setTextSize(1, 15);
                activityList.addView(editText);

                Button confirm = new Button(context);
                confirm.setText("Add");
                confirm.setTextColor(Color.WHITE);
                activityList.addView(confirm);

                Button cancel = new Button(context);
                cancel.setText("Cancel");
                cancel.setTextColor(Color.WHITE);
                activityList.addView(cancel);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = editText.getText().toString();
                        if (!text.equals("")) {
                            addActivity(text);

                            activityList.removeView(editText);
                            activityList.removeView(confirm);
                            activityList.removeView(cancel);

                            addButton.setVisibility(View.VISIBLE);
                        } else {
                            editText.setHintTextColor(Color.RED);
                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activityList.removeView(editText);
                        activityList.removeView(confirm);
                        activityList.removeView(cancel);

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