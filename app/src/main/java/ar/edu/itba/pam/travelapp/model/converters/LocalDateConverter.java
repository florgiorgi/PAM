package ar.edu.itba.pam.travelapp.model.converters;

import android.os.Build;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

public class LocalDateConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate toLocalDate(Long l) {
        if (l == null) {
            return null;
        }
        return Instant.ofEpochSecond(l).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long fromCalendar(LocalDate localDate){
        return localDate == null ? null : localDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }
}
