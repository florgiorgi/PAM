package ar.edu.itba.pam.travelapp.model.converters;

import android.os.Build;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

public class LocalDateTimeConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDateTime toLocalDateTime(Long l) {
        if (l == null) {
            return null;
        }
        return Instant.ofEpochSecond(l).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long fromLocalDateTime(LocalDateTime localDateTime){
        return localDateTime == null ? null : localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

}
