package ar.edu.itba.pam.travelapp.tripdetail;

import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import ar.edu.itba.pam.travelapp.tripdetail.draganddrop.LinearLayoutDropListener;
import ar.edu.itba.pam.travelapp.tripdetail.draganddrop.DayActivityDragOnLongClickListener;


public class DayViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public View titleView;
    public ImageButton addButton;

    private final LinearLayout activityList;
    private final LinearLayout add_activity;

    private ActivityEventListener listener;
    private ImageView arrow;
    private View divider;

    private LocalDate date;

    public DayViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        this.activityList = view.findViewById(R.id.list_of_activities);
        this.activityList.setOnDragListener(new LinearLayoutDropListener(this));
        this.titleView = view.findViewById(R.id.day_card_title);
        this.titleView.setOnDragListener(new LinearLayoutDropListener(this));
        this.addButton = view.findViewById(R.id.add_button);
        this.add_activity = view.findViewById(R.id.add_activity);
        setUpClickOnCardToExpand();
    }

    public void setOnClickListener(ActivityEventListener listener) {
        this.listener = listener;
    }

    private void setUpActivities(List<Activity> activities) {
        activityList.removeAllViews();
        for (Activity a : activities) {
            TextView textView = new TextView(view.getContext());
            textView.setOnLongClickListener(new DayActivityDragOnLongClickListener(a, textView));

            EditText editText = new EditText(view.getContext());
            ImageButton deleteButton = new ImageButton(view.getContext());
            ImageButton confirmButton = new ImageButton(view.getContext());
            LinearLayout editAndCancel = new LinearLayout(view.getContext());

            textView.setText(a.getName());
            editText.setText(a.getName());
            deleteButton.setBackgroundResource(R.drawable.ic_delete);
            confirmButton.setBackgroundResource(R.drawable.check_icon);
            editText.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
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
            LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(
                    70,
                    70
            );
            LinearLayout.LayoutParams editAndCancelParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    150
            );
            params.setMargins(2, 4, 2, 4);
            editParams.setMargins(0, 0, 0, 0);
            confirmButtonParams.setMargins(20, 50, 20, 0);
            deleteButtonParams.setMargins(20, 42, 15, 0);
            editAndCancelParams.setMargins(-10, -20, 0, -20);

            textView.setLayoutParams(params);
            editText.setLayoutParams(editParams);
            deleteButton.setLayoutParams(deleteButtonParams);
            confirmButton.setLayoutParams(confirmButtonParams);
            editAndCancel.setLayoutParams(editAndCancelParams);

            editAndCancel.setOrientation(LinearLayout.HORIZONTAL);
            editAndCancel.addView(editText);
            editAndCancel.addView(confirmButton);
            editAndCancel.addView(deleteButton);

            textView.setTextSize(1, 16);
            editText.setTextSize(1, 16);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setMaxLines(1);
            editText.setImeOptions(EditorInfo.IME_ACTION_SEND);

            textView.setOnClickListener(v -> {
                textView.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                editAndCancel.setVisibility(View.VISIBLE);
            });
            confirmButton.setOnClickListener(v1 -> {
                listener.onEditActivity(a, editText.getText().toString());
                editText.setText(a.getName());
                textView.setVisibility(View.VISIBLE);
                editText.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
                editAndCancel.setVisibility(View.GONE);
            });
            deleteButton.setOnClickListener(v2 -> {
                listener.onDeleteActivity(a);
                editText.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
                editAndCancel.setVisibility(View.GONE);
            });
            editText.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    listener.onEditActivity(a, editText.getText().toString());
                    editText.setText(a.getName());
                    textView.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    confirmButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.GONE);
                    editAndCancel.setVisibility(View.GONE);
                    return true;
                }
                return false;
            });
            activityList.addView(textView);
            activityList.addView(editAndCancel);
        }
    }

    public void bind(final DayDto activitiesAndForecast, final int position, LocalDate date) {
        this.date = date;
        final TextView dayNum = itemView.findViewById(R.id.day_number);
        final TextView minTemp = itemView.findViewById(R.id.min_temperature);
        final TextView maxTemp = itemView.findViewById(R.id.max_temperature);
        final TextView tempDivider = itemView.findViewById(R.id.temp_divider);
        final ImageView weatherIcon = itemView.findViewById(R.id.weather_icon);
        String dayString = view.getContext().getString(R.string.day) + " " + (position + 1);
        dayNum.setText(dayString);
        Forecast forecasts = activitiesAndForecast.getDayForecast();
        minTemp.setText(forecasts == null ? "" : Math.round(activitiesAndForecast.getDayForecast().getTemperature().getMinimum().getValue()) + "ºC");
        if (forecasts != null)
            tempDivider.setVisibility(View.VISIBLE);
        maxTemp.setText(forecasts == null ? "" : Math.round(activitiesAndForecast.getDayForecast().getTemperature().getMaximum().getValue()) + "ºC");
        if (forecasts != null) {
            int iconography = forecasts.getDay().getIcon();
            switch (iconography) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 32:
                case 33:
                case 34:
                    weatherIcon.setBackgroundResource(R.drawable.sunny);
                    break;
                case 6:
                case 35:
                case 36:
                case 37:
                    weatherIcon.setBackgroundResource(R.drawable.partly_cloudy);
                    break;
                case 7:
                case 8:
                case 9:
                case 38:
                    weatherIcon.setBackgroundResource(R.drawable.cloudy);
                    break;
                default:
                    weatherIcon.setBackgroundResource(R.drawable.rainy);
                    break;
            }
        }
        setUpActivities(activitiesAndForecast.getDayActivities());
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
                add_activity.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.arrow_up);
            }
        });
    }

    private void setUpAddButton(LocalDate date) {

        addButton.setOnClickListener(v -> {
            addButton.setVisibility(View.GONE);

            LinearLayout layout = view.findViewById(R.id.add_activity);
            EditText editText = view.findViewById(R.id.enter_new_activity);
            ImageButton cancelButton = view.findViewById(R.id.cancel_button);
            ImageButton confirmButton = view.findViewById(R.id.confirm_button);

            layout.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);

            confirmButton.setOnClickListener(v1 -> {
                String text = editText.getText().toString();
                if (!text.equals("")) {
                    listener.onClickNewActivity(text, date);
                    layout.setVisibility(View.GONE);
                    editText.setText("");
                    editText.setHintTextColor(Color.GRAY);
                    editText.setVisibility(View.GONE);
                    confirmButton.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.GONE);
                    addButton.setVisibility(View.VISIBLE);
                } else {
                    editText.setHintTextColor(Color.RED);
                }
            });
            cancelButton.setOnClickListener(v2 -> {
                layout.setVisibility(View.GONE);
                editText.setText("");
                editText.setHintTextColor(Color.GRAY);
                editText.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                addButton.setVisibility(View.VISIBLE);
            });
            editText.setOnEditorActionListener((v12, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (!editText.getText().toString().equals("")) {
                        listener.onClickNewActivity(editText.getText().toString(), date);
                        layout.setVisibility(View.GONE);
                        editText.setText("");
                        editText.setHintTextColor(Color.GRAY);
                        editText.setVisibility(View.GONE);
                        confirmButton.setVisibility(View.GONE);
                        cancelButton.setVisibility(View.GONE);
                        addButton.setVisibility(View.VISIBLE);
                        return true;
                    } else {
                        editText.setHintTextColor(Color.RED);
                    }
                }
                return false;
            });
        });
    }

    public View getView() {
        return view;
    }

    public ActivityEventListener getListener() {
        return listener;
    }

    public LocalDate getDate() {
        return date;
    }
}