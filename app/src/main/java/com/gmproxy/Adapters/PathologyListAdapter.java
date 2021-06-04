package com.gmproxy.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.pastilarma.PathologiesSearchScreen;
import com.gmproxy.pastilarma.UserAddScreen;

import java.util.List;

/**
 * Refer to UserListAdapter.
 */
public class PathologyListAdapter extends ListAdapter<Pathology, PathologyViewHolder> {

    private int positionF = RecyclerView.SCROLLBAR_POSITION_DEFAULT;
    private Pathology path;

    public PathologyListAdapter(@NonNull DiffUtil.ItemCallback<Pathology> diffCallback) {
        super(diffCallback);
    }

    @Override
    public PathologyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PathologyViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(PathologyViewHolder holder, int position) {
        Pathology current = getItem(position);
        holder.bind(current.getPathologyName());
    }


    public Pathology getCurrentObject(int position){
        path = getItem(position);
        return path;
    }



    public static class UserDiff extends DiffUtil.ItemCallback<Pathology> {

        @Override
        public boolean areItemsTheSame(@NonNull Pathology oldItem, @NonNull Pathology newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pathology oldItem, @NonNull Pathology newItem) {
            return oldItem.getPathologyName().equals(newItem.getPathologyName());
        }
    }


}
