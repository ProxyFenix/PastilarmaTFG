package com.gmproxy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmproxy.Entities.User;
import com.gmproxy.pastilarma.R;

/**
 * Gives the objects to populate the recyclerview to the holder. Not much else, we also use it to get the clicked object.
 */
public class UserListAdapter extends ListAdapter<User, UserViewHolder> {
    private User usr;

    public UserListAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UserViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User current = getItem(position);
        holder.bind(current.getUser_name(), current.getUser_surname());
    }

    public User getCurrentObject(int position){
        usr = getItem(position);
        return usr;
    }

    public static class UserDiff extends DiffUtil.ItemCallback<User> {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUser_name().equals(newItem.getUser_name());
        }
    }
}
