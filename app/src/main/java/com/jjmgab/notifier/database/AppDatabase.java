package com.jjmgab.notifier.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Notification.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotificationDao notificationDao();
}
