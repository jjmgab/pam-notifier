package com.jjmgab.notifier;

import android.content.Context;

import androidx.room.Room;

import com.jjmgab.notifier.database.AppDatabase;
import com.jjmgab.notifier.database.Notification;
import com.jjmgab.notifier.helpers.PreferenceHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
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

    public static void init(Context appContext, NotificationItemFragment fragment) {
        db = Room.databaseBuilder(appContext, AppDatabase.class, "notifier.db")
                .allowMainThreadQueries()
                .build();

        if (PreferenceHelper.getBooleanPref(appContext, PreferenceHelper.PREF_DEBUG_CLEAN_START)) {
            db.clearAllTables();
        }

        // does not show notifications in the past
        List<Notification> notificationList = new ArrayList<>();
        for (Notification n : db.notificationDao().getAll()) {
            if (n.date.compareTo(LocalDateTime.now()) > 0) {
                notificationList.add(n);
            }
        }

        // sorts the list
        notificationList.sort(new Comparator<Notification>() {
            @Override
            public int compare(Notification o1, Notification o2) {
                return o1.date.compareTo(o2.date);
            }
        });

        for (Notification n : notificationList) {
            ITEMS.add(n);
            ITEM_MAP.put(String.valueOf(n.id), n);
        }

        mFragment = fragment;
    }

    public static void addItem(Notification item) {
        db.notificationDao().insertAll(item);
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.id), item);

        // sorts the list
        ITEMS.sort(new Comparator<Notification>() {
            @Override
            public int compare(Notification o1, Notification o2) {
                return o1.date.compareTo(o2.date);
            }
        });

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
