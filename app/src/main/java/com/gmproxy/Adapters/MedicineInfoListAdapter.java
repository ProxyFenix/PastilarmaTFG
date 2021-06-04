package com.gmproxy.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.gmproxy.Entities.Medicine;

/**
 * Refer to UserListAdapter.
 */
public class MedicineInfoListAdapter extends ListAdapter<Medicine, MedicineInfoViewHolder> {
    private Medicine med;

    public MedicineInfoListAdapter(@NonNull DiffUtil.ItemCallback<Medicine> diffCallback) {
        super(diffCallback);
    }

    @Override
    public MedicineInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MedicineInfoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(MedicineInfoViewHolder holder, int position) {
        Medicine current = getItem(position);
        holder.bind(current.getMedicineName());
    }

    public Medicine getCurrentObject(int position){
        med = getItem(position);
        return med;
    }

    public static class UserDiff extends DiffUtil.ItemCallback<Medicine> {

        @Override
        public boolean areItemsTheSame(@NonNull Medicine oldItem, @NonNull Medicine newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Medicine oldItem, @NonNull Medicine newItem) {
            return oldItem.getMedicineName().equals(newItem.getMedicineName());
        }
    }
}
