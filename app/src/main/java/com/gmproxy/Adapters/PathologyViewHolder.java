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
public class PathologyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView objItemView;
    Pathology path;

    public PathologyViewHolder(View itemView) {
        super(itemView);
        objItemView = itemView.findViewById(R.id.TextView);
    }

    public void bind(String current) {
        objItemView.setText(current);
    }

    static PathologyViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pathologies_item, parent, false);
        return new PathologyViewHolder(view);
    }


    @Override
    public void onClick(View v) {
    }
}


