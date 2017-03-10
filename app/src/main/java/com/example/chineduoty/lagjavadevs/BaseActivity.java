package com.example.chineduoty.lagjavadevs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.chineduoty.lagjavadevs.listeners.NetworkStateChanged;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by chineduoty on 3/6/17.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private int resourceLayout;
    public Toolbar toolbar ;

    public int getResourceLayout() {
        return resourceLayout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceLayout());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NetworkStateChanged event) {
        if (!event.isInternetConnected()) {
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this); // register EventBus
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this); // unregister EventBus
        super.onStop();
    }
}
