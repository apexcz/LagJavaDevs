package com.example.chineduoty.lagjavadevs.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by chineduoty on 3/8/17.
 */

public class NetworkStateReceiver extends BroadcastReceiver {
    //post event if there is no internet connection
    public void onReceive(Context context, Intent intent) {
        //super.onReceive(context, intent);
        if (intent.getExtras() != null) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                // there is Internet connection
            } else if (intent
                    .getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                // no Internet connection, send network state changed
                EventBus.getDefault().post(new NetworkStateChanged(false));
            }
        }
    }
}
