package com.jjmgab.notifier;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.jjmgab.notifier.helpers.DeviceStateHelper;
import com.jjmgab.notifier.helpers.PreferenceHelper;

public class NotificationPublisher extends BroadcastReceiver {
    public static final String NOTIFICATION_ID = "notification-id";
    public static final String NOTIFICATION = "notification";

    public static final String CONDITIONS = "Conditions:";

    public static final String WIFI = "\nWIFI ";
    public static final String BLUETOOTH = "\nBLUETOOTH ";

    public static final String ENABLED = "ENABLED";
    public static final String DISABLED = "DISABLED";
    public static final String CONNECTED = "CONNECTED";
    public static final String DISCONNECTED = "DISCONNECTED";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        StringBuilder builder = new StringBuilder(CONDITIONS);

        // verify here if the conditions are fulfilled
        boolean areConditionsFulfilled = true;

        if (PreferenceHelper.getBooleanPref(context, PreferenceHelper.PREF_BLUETOOTH_ENABLED)) {
            builder.append(BLUETOOTH);
            if (DeviceStateHelper.isBluetoothEnabled()) {
                builder.append(ENABLED);
                areConditionsFulfilled = areConditionsFulfilled && true;
            }
            else {
                builder.append(DISABLED);
                areConditionsFulfilled = areConditionsFulfilled && false;
            }
        }
        if (PreferenceHelper.getBooleanPref(context, PreferenceHelper.PREF_WIFI_ENABLED)) {
            builder.append(WIFI);
            if (DeviceStateHelper.isWifiEnabled()) {
                builder.append(ENABLED);
                areConditionsFulfilled = areConditionsFulfilled && true;
            }
            else {
                builder.append(DISABLED);
                areConditionsFulfilled = areConditionsFulfilled && false;
            }
        }
        if (PreferenceHelper.getBooleanPref(context, PreferenceHelper.PREF_WIFI_CONNECTED)) {
            builder.append(WIFI);
            if (DeviceStateHelper.isWifiConnected()) {
                builder.append(CONNECTED);
                areConditionsFulfilled = areConditionsFulfilled && true;
            }
            else {
                builder.append(DISCONNECTED);
                areConditionsFulfilled = areConditionsFulfilled && false;
            }
        }

        builder.append("\n" + String.valueOf(id));

        if (PreferenceHelper.getBooleanPref(context, PreferenceHelper.PREF_DEBUG_TOASTS)) {
            Toast.makeText(context, builder.toString(), Toast.LENGTH_SHORT).show();
        }

        if (areConditionsFulfilled) {
            notificationManager.notify(id, notification);
        }
    }
}
