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

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        StringBuilder builder = new StringBuilder();

        // verify here if the conditions are fulfilled
        boolean areConditionsFulfilled = true;

        if (PreferenceHelper.getBooleanPref(context, PreferenceHelper.PREF_BLUETOOTH_ENABLED)) {
            if (DeviceStateHelper.isBluetoothEnabled()) {
                builder.append("BLUETOOTH ENABLED\n");
                areConditionsFulfilled = areConditionsFulfilled && true;
            }
            else {
                builder.append("BLUETOOTH DISABLED\n");
                areConditionsFulfilled = areConditionsFulfilled && false;
            }
        }
        if (PreferenceHelper.getBooleanPref(context, PreferenceHelper.PREF_WIFI_ENABLED)) {
            if (DeviceStateHelper.isWifiEnabled()) {
                builder.append("WIFI ENABLED\n");
                areConditionsFulfilled = areConditionsFulfilled && true;
            }
            else {
                builder.append("WIFI DISABLED\n");
                areConditionsFulfilled = areConditionsFulfilled && false;
            }
        }
        if (PreferenceHelper.getBooleanPref(context, PreferenceHelper.PREF_WIFI_CONNECTED)) {
            if (DeviceStateHelper.isWifiConnected()) {
                builder.append("WIFI CONNECTED\n");
                areConditionsFulfilled = areConditionsFulfilled && true;
            }
            else {
                builder.append("WIFI DISCONNECTED\n");
                areConditionsFulfilled = areConditionsFulfilled && false;
            }
        }

        if (PreferenceHelper.getBooleanPref(context, PreferenceHelper.PREF_DEBUG_TOASTS)) {
            Toast.makeText(context, builder.toString(), Toast.LENGTH_SHORT).show();
        }

        if (areConditionsFulfilled) {
            notificationManager.notify(id, notification);
        }
    }
}
