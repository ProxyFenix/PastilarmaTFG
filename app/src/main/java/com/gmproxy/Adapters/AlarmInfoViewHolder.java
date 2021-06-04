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
public class AlarmInfoViewHolder extends RecyclerView.ViewHolder {
    public final TextView objItemView;
    public final TextView medObjItemView;

    private AlarmInfoViewHolder(View itemView) {
        super(itemView);
        objItemView = itemView.findViewById(R.id.textViewH);
        medObjItemView = itemView.findViewById(R.id.medTextView);
    }

    public void bind(String text, String text2) {
        objItemView.setText(text);
        medObjItemView.setText(text2);
    }

    static AlarmInfoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alus_item, parent, false);
        return new AlarmInfoViewHolder(view);
    }
}


