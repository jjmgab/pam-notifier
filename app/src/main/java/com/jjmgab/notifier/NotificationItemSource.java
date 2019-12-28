package com.jjmgab.notifier;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for providing {@link NotificationItem} list.
 */
public class NotificationItemSource {

    public static final List<NotificationItem> ITEMS = new ArrayList<>();

    public static final Map<String, NotificationItem> ITEM_MAP = new HashMap<>();

    // generation
    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createNotificationItem(i));
        }
    }

    private static void addItem(NotificationItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static NotificationItem createNotificationItem(int position) {
        return new NotificationItem(String.valueOf(position), "Item " + position, makeDetails(position), makeDate(), makeTime());
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    private static LocalDate makeDate() {
        return LocalDate.of(2019, 1, 1);
    }

    private static LocalTime makeTime() {
        return LocalTime.of(12, 30);
    }
    // end generation
}
