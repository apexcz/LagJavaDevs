package com.example.chineduoty.lagjavadevs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chineduoty.lagjavadevs.adapters.UserAdapter;
import com.example.chineduoty.lagjavadevs.fragments.MainFragment;
import com.example.chineduoty.lagjavadevs.listeners.ClickListener;
import com.example.chineduoty.lagjavadevs.listeners.RecyclerTouchListener;
import com.example.chineduoty.lagjavadevs.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    @Override
    public int getResourceLayout() {
        return R.layout.activity_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new MainFragment()).commit();

    }

}
