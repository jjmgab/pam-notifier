package com.jjmgab.notifier.database;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class TimestampConverter {

    @TypeConverter
    public LocalDateTime fromTimestamp(Long value) {
        return value == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(value),
                TimeZone.getDefault().toZoneId());
    }

    @TypeConverter
    public Long dateToTimestamp(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
    }
}
