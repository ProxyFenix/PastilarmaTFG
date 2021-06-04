package com.gmproxy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gmproxy.pastilarma.R;

/**
 *This and all holders just basically populate the recycler view, and bind the correct texts.
 */
class UserViewHolder extends RecyclerView.ViewHolder {
    private final TextView objItemView;

    private UserViewHolder(View itemView) {
        super(itemView);
        objItemView = itemView.findViewById(R.id.TextView);
    }

    public void bind(String text, String text2) {
        objItemView.setText(text + "\n" + text2);
    }

    static UserViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_item, parent, false);
        return new UserViewHolder(view);
    }
}
