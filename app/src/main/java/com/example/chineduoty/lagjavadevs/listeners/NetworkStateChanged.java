package com.example.chineduoty.lagjavadevs.listeners;

/**
 * Created by chineduoty on 3/8/17.
 */

public class NetworkStateChanged {

    private boolean isInternetConnected;

    public NetworkStateChanged(boolean isInternetConnected) {
        this.isInternetConnected = isInternetConnected;
    }

    public boolean isInternetConnected() {
        return this.isInternetConnected;
    }
}