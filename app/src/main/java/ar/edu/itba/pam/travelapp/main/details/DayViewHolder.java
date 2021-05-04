package ar.edu.itba.pam.travelapp.main.details;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.pam.travelapp.R;

public class DayViewHolder extends RecyclerView.ViewHolder {

    public View view;
    public View titleView;
    private LinearLayout activityList;
    private View divider;
    private ImageView arrow;

    public DayViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        titleView = view.findViewById(R.id.day_card_title);
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                divider = view.findViewById(R.id.divider);
                activityList = view.findViewById(R.id.list_of_activities);
                arrow = view.findViewById(R.id.arrow_down);

                if (divider.getVisibility() == View.GONE) {
                    divider.setVisibility(View.VISIBLE);
                    activityList.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.arrow_down);
                } else {
                    divider.setVisibility(View.GONE);
                    activityList.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.arrow_up);

                }
            }
        });

    }

    public void bind(final String text) {
        final TextView textView = itemView.findViewById(R.id.day_number);

        textView.setText(text);
    }
}
