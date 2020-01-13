package com.jjmgab.notifier.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public final class PreferenceHelper {
    public static final String PREF_BLUETOOTH_ENABLED = "bluetooth";
    public static final String PREF_WIFI_ENABLED = "wifi_enabled";
    public static final String PREF_WIFI_CONNECTED = "wifi_connected";
    public static final String PREF_BATTERY_CHARGING = "battery_charging";

    public static final String PREF_DEBUG_TOASTS = "debug_toasts";
    public static final String PREF_DEBUG_FIXED_TIME = "debug_fixed_time";
    public static final String PREF_DEBUG_CLEAN_START = "debug_clean_start";

    public static boolean getBooleanPref(Context context, String name) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(name, false);
    }
}
