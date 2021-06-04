package com.gmproxy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gmproxy.pastilarma.R;

/**
 * Refer to UserViewHolder.
 */
class MedicineViewHolder extends RecyclerView.ViewHolder {
    public final TextView objItemView;

    private MedicineViewHolder(View itemView) {
        super(itemView);
        objItemView = itemView.findViewById(R.id.TextView);
    }

    public void bind(String text) {
        objItemView.setText(text);
    }

    static MedicineViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pathologies_item, parent, false);
        return new MedicineViewHolder(view);
    }
}
