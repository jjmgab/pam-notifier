package com.jjmgab.notifier.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDateTime;

@Entity
public class Notification {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "details")
    public String details;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public LocalDateTime date;

    public Notification() {}

    @Ignore
    public Notification(String title, String details, LocalDateTime date) {
        this.title = title;
        this.details = details;
        this.date = date;
    }
}
