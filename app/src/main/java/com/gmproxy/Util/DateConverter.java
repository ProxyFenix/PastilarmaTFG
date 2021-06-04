package com.gmproxy.Util;

import androidx.room.TypeConverter;

import java.util.Calendar;

// example converter for java.util.Date
public class DateConverter {

    @TypeConverter
    public Calendar fromTimestamp(Long value) {
        if (value == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(value);
            return cal;
        }
    }

    @TypeConverter
    public Long dateToTimestamp(Calendar date) {
        if (date == null) {
            return null;
        } else {
            return date.getTimeInMillis();
        }
    }
}
