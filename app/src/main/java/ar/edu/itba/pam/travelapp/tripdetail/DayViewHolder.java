package ar.edu.itba.pam.travelapp.tripdetail;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
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
import ar.edu.itba.pam.travelapp.model.dtos.DayDto;
import ar.edu.itba.pam.travelapp.model.weather.dtos.forecast.Forecast;

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
    private Button cancel;
    private LinearLayout buttons;

    private Context context;

    private OnNewActivityClickedListener listener;
    private Forecast forecasts;

    public DayViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.view = itemView;
        this.context = context;
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

    public void setOnClickListener(OnNewActivityClickedListener listener) {
        this.listener = listener;
    }

    private void setUpActivities(List<Activity> activities) {
        activityList.removeAllViews();
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

    public void bind(final DayDto activities, final int position, LocalDate date) {
        final TextView dayNum = itemView.findViewById(R.id.day_number);
        final TextView minTemp = itemView.findViewById(R.id.min_temperature);
        final TextView maxTemp = itemView.findViewById(R.id.max_temperature);
        final ImageView weatherIcon = itemView.findViewById(R.id.weather_icon);
        dayNum.setText("Day " + (position + 1));
        forecasts = activities.getDayForecast();
        minTemp.setText(forecasts == null ? "--" : activities.getDayForecast().getTemperature().getMinimum().getValue() + "ºC");
        maxTemp.setText(forecasts == null ? "--" : activities.getDayForecast().getTemperature().getMaximum().getValue() + "ºC");
        if (forecasts != null) {
            switch (forecasts.getDay().getIcon()) {
                case 1: case 2: case 3: case 4: case 5:
                    System.out.println("it's sunny");
//                    weatherIcon.setImageDrawable(R.drawable.sunny);
                    break;
            }
        }
        setUpActivities(activities.getDayActivities());
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
                    listener.onClick(text, date);
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