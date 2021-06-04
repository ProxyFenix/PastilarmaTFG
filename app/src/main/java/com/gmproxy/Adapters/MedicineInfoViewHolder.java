package com.gmproxy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gmproxy.pastilarma.R;

/**
 * Refer to UserListAdapter.
 */
public class MedicineInfoViewHolder extends RecyclerView.ViewHolder {
    public final TextView medObjItemView;

    private MedicineInfoViewHolder(View itemView) {
        super(itemView);
        medObjItemView = itemView.findViewById(R.id.medTextView);
    }

    public void bind(String text) {
        medObjItemView.setText(text);
    }

    static MedicineInfoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paus_item, parent, false);
        return new MedicineInfoViewHolder(view);
    }
}


