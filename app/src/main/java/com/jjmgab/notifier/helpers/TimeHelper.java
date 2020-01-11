package com.jjmgab.notifier.helpers;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class TimeHelper {

    public static long getEpoch(LocalDateTime date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return date.atZone(zoneId).toEpochSecond();
    }

    /**
     * Returns epoch time difference between two dates in seconds.
     * @param date1
     * @param date2
     * @return
     */
    public static long calculateEpochTimeDifference(LocalDateTime date1, LocalDateTime date2) {
        long epochDate1 = getEpoch(date1);
        long epochDate2 = getEpoch(date2);

        if (epochDate1 > epochDate2) {
            throw new IllegalArgumentException("First date must be before second date!");
        }
        return epochDate2 - epochDate1;
    }

    public static int getSecondsToDateFromNow(LocalDateTime date) {
        return 1000 * (int)calculateEpochTimeDifference(LocalDateTime.now(ZoneId.systemDefault()), date);
    }
}
