package com.jjmgab.notifier;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class holding notification information.
 */
public class NotificationItem {

    public final String id;
    public final String title;
    public final String details;
    public final LocalDate date;
    public final LocalTime time;

    public NotificationItem(String id, String title, String details, LocalDate date, LocalTime time) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return title;
    }

}
