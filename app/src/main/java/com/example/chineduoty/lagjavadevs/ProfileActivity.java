package com.example.chineduoty.lagjavadevs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.chineduoty.lagjavadevs.fragments.ProfileFragment;

/**
 * Created by chineduoty on 3/6/17.
 */

public class ProfileActivity extends BaseActivity {


    @Override
    public int getResourceLayout() {
        return R.layout.activity_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userString = getIntent().getStringExtra("user");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, ProfileFragment.newInstance(userString)).commit();
    }
}
