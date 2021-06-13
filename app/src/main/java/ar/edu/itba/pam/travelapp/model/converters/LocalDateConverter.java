package ar.edu.itba.pam.travelapp.model.converters;

import java.time.LocalDate;

import androidx.room.TypeConverter;

public class LocalDateConverter {

    @TypeConverter
    public static LocalDate toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return LocalDate.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }

}
