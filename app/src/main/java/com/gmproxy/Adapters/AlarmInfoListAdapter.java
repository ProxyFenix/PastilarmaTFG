package com.gmproxy.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.HourMedicine;
import com.gmproxy.Entities.Medicine;
import com.gmproxy.ViewModels.AlarmInfoViewModel;

/**
 * Refer to UserListAdapter.
 */
public class AlarmInfoListAdapter extends ListAdapter<HourMedicine, AlarmInfoViewHolder> {
    private HourMedicine al;

    public AlarmInfoListAdapter(@NonNull DiffUtil.ItemCallback<HourMedicine> diffCallback) {
        super(diffCallback);
    }

    @Override
    public AlarmInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AlarmInfoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AlarmInfoViewHolder holder, int position) {
        HourMedicine current = getItem(position);
        holder.bind(current.getHora(),current.getNombre());
    }

    public HourMedicine getCurrentObject(int position){
        al = getItem(position);
        return al;
    }

    public static class UserDiff extends DiffUtil.ItemCallback<HourMedicine> {

        @Override
        public boolean areItemsTheSame(@NonNull HourMedicine oldItem, @NonNull HourMedicine newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull HourMedicine oldItem, @NonNull HourMedicine newItem) {
            return oldItem.getHora()==(newItem.getHora()) && oldItem.getNombre().equals(newItem.getNombre());
        }
    }
}
