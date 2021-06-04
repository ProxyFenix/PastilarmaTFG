package com.gmproxy.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.pastilarma.PathologiesSearchScreen;
import com.gmproxy.pastilarma.UserAddScreen;

/**
 * Refer to UserListAdapter.
 */
public class MedicineListAdapter extends ListAdapter<Medicine, MedicineViewHolder> {
    private Medicine med;

    public MedicineListAdapter(@NonNull DiffUtil.ItemCallback<Medicine> diffCallback) {
        super(diffCallback);
    }

    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MedicineViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(MedicineViewHolder holder, int position) {
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
