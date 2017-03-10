package com.example.chineduoty.lagjavadevs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.chineduoty.lagjavadevs.Constants;
import com.example.chineduoty.lagjavadevs.MainActivity;
import com.example.chineduoty.lagjavadevs.ProfileActivity;
import com.example.chineduoty.lagjavadevs.R;
import com.example.chineduoty.lagjavadevs.adapters.UserAdapter;
import com.example.chineduoty.lagjavadevs.listeners.ClickListener;
import com.example.chineduoty.lagjavadevs.listeners.RecyclerTouchListener;
import com.example.chineduoty.lagjavadevs.models.User;
import com.google.gson.Gson;
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

/**
 * Created by chineduoty on 3/7/17.
 */

public class MainFragment extends Fragment {

    private RecyclerView usersRecyclerView;
    private UserAdapter usersAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> lstUsers = new ArrayList<>();
    private ProgressBar userLoader;
    private boolean isGrid = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        userLoader = (ProgressBar) rootView.findViewById(R.id.userLoader);
        usersRecyclerView = (RecyclerView) rootView.findViewById(R.id.users_rv);
        usersRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        usersRecyclerView.setLayoutManager(layoutManager);

        usersAdapter = new UserAdapter(getContext(),lstUsers);
        usersRecyclerView.setAdapter(usersAdapter);

        try {
            fetchUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        usersRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),usersRecyclerView,
                new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        User user = lstUsers.get(position);
                        Intent intent = new Intent(getActivity(),ProfileActivity.class);
                        intent.putExtra("user", new Gson().toJson(user));
                        startActivity(intent);
//                        Toast.makeText(getApplicationContext(),user.getLogin() + " clicked.",Toast.LENGTH_LONG)
//                                .show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
        return rootView;
    }

    void fetchUsers() throws Exception{
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL+"/search/users").newBuilder();
        urlBuilder.addQueryParameter("q", "location:lagos");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                //.header("Authorization", "your token")
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {

                    Type userlistType = new TypeToken<List<User>>(){}.getType();
                    String resp = response.body().string();
                    String usersArray = null;
                    try {
                        JSONObject obj = new JSONObject(resp);
                        usersArray = obj.getJSONArray("items").toString();

                    }
                    catch (JSONException ex){
                        ex.printStackTrace();
                    }

                    final List<User> userResponse = new Gson().fromJson(usersArray,userlistType);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lstUsers.addAll(userResponse);
                            usersAdapter.notifyDataSetChanged();
                            userLoader.setVisibility(View.GONE);
                            usersRecyclerView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                if(searchText != null && !searchText.isEmpty()){

                    searchText = searchText.toLowerCase();

                    List<User> filteredList = new ArrayList<User>();
                    for (User user : lstUsers){
                        if(user.getLogin().toLowerCase().contains(searchText))
                            filteredList.add(user);
                    }

                    usersAdapter.UpdateData(filteredList);
                }
                else {
                    usersAdapter.UpdateData(lstUsers);
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_arrange:
                isGrid = !isGrid;
                usersRecyclerView.setLayoutManager(isGrid ? new LinearLayoutManager(getActivity()) : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                usersRecyclerView.setAdapter(usersAdapter);
                break;
            case R.id.action_search:

                return true;
            case R.id.action_settings:
                return true;
            default:return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
