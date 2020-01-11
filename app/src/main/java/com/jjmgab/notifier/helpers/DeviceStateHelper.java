package com.jjmgab.notifier.helpers;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import static android.content.Context.WIFI_SERVICE;

public final class DeviceStateHelper {
    private static Context mAppContext;

    public static void init(Context context) {
        mAppContext = context;
    }

    public static boolean isBluetoothEnabled() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled();
    }

    public static boolean isWifiEnabled() {
        return getWifiManager().isWifiEnabled();
    }

    public static boolean isWifiConnected() {
        if (isWifiEnabled()) {
            WifiInfo wifiInfo = getWifiManager().getConnectionInfo();

            return wifiInfo != null && wifiInfo.getNetworkId() != -1;
        }
        return false;
    }

    private static WifiManager getWifiManager() {
        return (WifiManager) mAppContext.getSystemService(WIFI_SERVICE);
    }
}
