package com.example.chineduoty.lagjavadevs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chineduoty.lagjavadevs.R;
import com.example.chineduoty.lagjavadevs.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chineduoty on 3/5/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> lstUsers;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageUrl;
        public TextView username;

        public ViewHolder(View view) {
            super(view);
            imageUrl = (ImageView)view.findViewById(R.id.profile_image);
            username = (TextView)view.findViewById(R.id.username);
        }
    }

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        lstUsers = users;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = lstUsers.get(position);
        Picasso.with(context).load(user.getAvatar_url())
               .placeholder(R.drawable.avatar).into(holder.imageUrl);
        holder.username.setText(user.getLogin());
    }

    @Override
    public int getItemCount() {
        return lstUsers.size();
    }

    public void UpdateData(List<User> itemCollections)
    {
        lstUsers = new ArrayList<>();
        lstUsers.addAll(itemCollections);
        notifyDataSetChanged();
    }
}
