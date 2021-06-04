package com.gmproxy.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmproxy.Entities.Pathology;

/**
 * Refer to UserListAdapter.
 */
public class PathologyInfoListAdapter extends ListAdapter<Pathology, PathologyInfoViewHolder> {

    private int positionF = RecyclerView.SCROLLBAR_POSITION_DEFAULT;
    private Pathology path;

    public PathologyInfoListAdapter(@NonNull DiffUtil.ItemCallback<Pathology> diffCallback) {
        super(diffCallback);
    }

    @Override
    public PathologyInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PathologyInfoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(PathologyInfoViewHolder holder, int position) {
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
