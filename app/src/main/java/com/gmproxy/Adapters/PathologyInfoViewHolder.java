package com.gmproxy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gmproxy.Entities.Pathology;
import com.gmproxy.pastilarma.R;

/**
 * Refer to UserViewHolder.
 */
public class PathologyInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView objItemView;
    Pathology path;

    public PathologyInfoViewHolder(View itemView) {
        super(itemView);
        objItemView = itemView.findViewById(R.id.pausTextView);
    }

    public void bind(String current) {
        objItemView.setText(current);
    }

    static PathologyInfoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paus_item, parent, false);
        return new PathologyInfoViewHolder(view);
    }


    @Override
    public void onClick(View v) {
    }
}


