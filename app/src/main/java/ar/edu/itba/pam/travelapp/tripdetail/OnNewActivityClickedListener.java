package ar.edu.itba.pam.travelapp.tripdetail;

import java.time.LocalDate;

public interface OnNewActivityClickedListener {

    void onClick(String name, LocalDate date);
}
