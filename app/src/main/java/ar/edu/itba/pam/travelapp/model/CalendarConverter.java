package ar.edu.itba.pam.travelapp.model;

import java.util.Calendar;

import androidx.room.TypeConverter;

public class CalendarConverter {

    @TypeConverter
    public static Calendar toCalendar(Long l) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        return c;
    }

    @TypeConverter
    public static Long fromCalendar(Calendar c){
        return c == null ? null : c.getTime().getTime();
    }
}
