package com.jjmgab.notifier;

import android.content.Context;

import androidx.room.Room;

import com.jjmgab.notifier.database.AppDatabase;
import com.jjmgab.notifier.database.Notification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for providing {@link Notification} list.
 */
public class NotificationItemSource {

    private static AppDatabase db;
    private static NotificationItemFragment mFragment;

    public static final List<Notification> ITEMS = new ArrayList<>();
    public static final Map<String, Notification> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    public static void init(Context appContext, NotificationItemFragment fragment, boolean initWithCleanup) {
        db = Room.databaseBuilder(appContext, AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();

        if (initWithCleanup) {
            db.clearAllTables();
        }

        for (Notification n : db.notificationDao().getAll()) {
            ITEMS.add(n);
            ITEM_MAP.put(String.valueOf(n.id), n);
        }

        mFragment = fragment;
    }

    public static void addItem(Notification item) {
        db.notificationDao().insertAll(item);
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.id), item);

        if (mFragment.getAdapter() != null) {
            mFragment.getAdapter().notifyDataSetChanged();
        }
    }

    // data seeding
    public static void seed() {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createSampleNotificationItem(i));
        }
    }

    public static Notification createSampleNotificationItem(int position) {
        return new Notification("Item " + position, makeDetails(position), LocalDateTime.now());
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}
