package ar.edu.itba.pam.travelapp.model.converters;

import android.os.Build;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

public class LocalDateConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
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
