package com.example.chineduoty.lagjavadevs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chineduoty.lagjavadevs.R;
import com.example.chineduoty.lagjavadevs.helpers.CommonUtils;
import com.example.chineduoty.lagjavadevs.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by chineduoty on 3/6/17.
 */

public class ProfileFragment extends Fragment {

    private static User user;

    public static ProfileFragment newInstance(String userString){
        ProfileFragment profileFragment = new ProfileFragment();
        user = new Gson().fromJson(userString,User.class);
        return profileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);

        TextView username = (TextView)rootView.findViewById(R.id.profile_name);
        ImageView profileImage = (ImageView)rootView.findViewById(R.id.profile_image);
        TextView profileUrl = (TextView)rootView.findViewById(R.id.profile_url);
        //ImageButton shareButton = (ImageButton)rootView.findViewById(R.id.share_button);

        Picasso.with(getContext()).load(user.getAvatar_url())
                .placeholder(R.drawable.avatar).into(profileImage);
        username.setText(user.getLogin());

        profileUrl.setText(Html.fromHtml("<a href='"+user.getHtml_url() +"'>"+user.getHtml_url()+"</a>"));
        profileUrl.setMovementMethod(LinkMovementMethod.getInstance());
        CommonUtils.stripUnderlines(profileUrl);


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_share:

                String shareMessage = String.format("Check out this awesome developer @%1$s, %2$s.",
                        user.getLogin(),user.getHtml_url());
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
