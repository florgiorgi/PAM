package ar.edu.itba.pam.travelapp.utils;

import android.widget.EditText;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDate parseDate(EditText inputField) {
        String dateInput = inputField.getText().toString();
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(dateInput, dateFormatter);
        } catch (DateTimeParseException e) {
            parsedDate = null;
        }
        return parsedDate;
    }

    public static LocalDateTime parseDateTime(EditText inputField) {
        String dateInput = inputField.getText().toString();
        LocalDateTime parsedDateTime;
        try {
            parsedDateTime = LocalDateTime.parse(dateInput, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            parsedDateTime = null;
        }
        return parsedDateTime;
    }
}
